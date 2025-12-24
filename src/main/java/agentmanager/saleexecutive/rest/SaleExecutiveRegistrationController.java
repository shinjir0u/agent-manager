package agentmanager.saleexecutive.rest;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import agentmanager.registration.model.Registration;
import agentmanager.registration.service.RegistrationService;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.service.SaleExecutiveService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/sale_executive")
public class SaleExecutiveRegistrationController {

	private final Logger logger = LogManager.getLogger(SaleExecutiveRegistrationController.class);

	private final RegistrationService registrationService;

	private final SaleExecutiveService saleExecutiveService;

	public SaleExecutiveRegistrationController(RegistrationService registrationService,
			SaleExecutiveService saleExecutiveService) {
		this.registrationService = registrationService;
		this.saleExecutiveService = saleExecutiveService;
	}

	@GetMapping("/{saleExecutiveId}/registration/list")
	public ResponseEntity<List<RegistrationResponse>> getRegistrationsBySaleExecutive(
			@PathVariable Long saleExecutiveId, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, RegistrationParams registrationParams) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);

		List<Registration> registrations = registrationService.getRegistrationsBySaleExecutive(saleExecutive, page,
				size, registrationParams.getAgent_name(), registrationParams.getPhone_number(),
				registrationParams.getRegistered_at());
		List<RegistrationResponse> response = registrations.stream()
				.map(registration -> new RegistrationResponse(registration.getId(), registration.getAgentName(),
						registration.getPhoneNumber(), registration.getRegisteredAt(),
						registration.getSaleExecutive().getUsername()))
				.collect(Collectors.toList());
		logger.info("Fetched registrations: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping(value = "/{saleExecutiveId}/registration/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegistrationResponse> createRegistration(@PathVariable Long saleExecutiveId,
			@RequestBody RegistrationRequest request) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);
		Registration registration = registrationService.addRegistration(request.getAgentName(),
				request.getPhoneNumber(), saleExecutive);
		RegistrationResponse response = new RegistrationResponse(registration.getId(), registration.getAgentName(),
				registration.getPhoneNumber(), registration.getRegisteredAt(),
				registration.getSaleExecutive().getUsername());

		URI registrationLocation = generateEntryUri(registration.getId());
		logger.info("Registration: {} created at: {}", response, registrationLocation);

		return ResponseEntity.created(registrationLocation).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PutMapping(value = "/registration/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegistrationResponse> updateRegistration(@PathVariable Long id,
			@RequestBody RegistrationRequest request) {
		Registration registration = registrationService.updateRegistration(id, request.getPhoneNumber());

		RegistrationResponse response = new RegistrationResponse(registration.getId(), registration.getAgentName(),
				registration.getPhoneNumber(), registration.getRegisteredAt(),
				registration.getSaleExecutive().getUsername());
		logger.info("Updated registration: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

	private SaleExecutive getSaleExecutive(Long id) {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutive == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);
		return saleExecutive;
	}

	@Data
	@NoArgsConstructor
	static class RegistrationRequest {

		@JsonProperty("agent_name")
		private String agentName;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class RegistrationResponse {

		private Long id;

		@JsonProperty("agent_name")
		private String agentName;

		@JsonProperty("phone_number")
		private String phoneNumber;

		@JsonProperty("registered_at")
		private Date registeredAt;

		@JsonProperty("registered_by")
		private String registeredBy;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class RegistrationParams {

		private String agent_name;

		private String phone_number;

		private Date registered_at;

	}

}
