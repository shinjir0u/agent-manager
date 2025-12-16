package agentmanager.saleexecutive.rest;

import java.net.URI;
import java.util.List;

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
public class SaleExecutiveController {

	private final Logger logger = LogManager.getLogger(SaleExecutiveController.class);

	private final RegistrationService registrationService;

	private final SaleExecutiveService saleExecutiveService;

	public SaleExecutiveController(RegistrationService registrationService, SaleExecutiveService saleExecutiveService) {
		this.registrationService = registrationService;
		this.saleExecutiveService = saleExecutiveService;
	}

	@GetMapping("/registration/list")
	public ResponseEntity<List<Registration>> getRegistrations() {
		List<Registration> registrations = registrationService.getRegistrations();
		logger.info("Fetched registrations: {}", registrations);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrations);
	}

	@GetMapping("/{saleExecutiveId}/registration/list")
	public ResponseEntity<List<Registration>> getRegistrationsBySaleExecutive(@PathVariable Long saleExecutiveId) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);
		List<Registration> registrations = registrationService.getRegistrationsBySaleExecutive(saleExecutive);
		logger.info("Fetched registrations: {}", registrations);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrations);
	}

	@PostMapping(value = "/{saleExecutiveId}/registration/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Registration> createRegistration(@PathVariable Long saleExecutiveId,
			@RequestBody Request request) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);
		Registration registration = registrationService.addRegistration(request.getAgentName(),
				request.getPhoneNumber(), saleExecutive);
		URI registrationLocation = generateEntryUri(registration.getId());
		logger.info("Registration: {} created at: {}", registration, registrationLocation);

		return ResponseEntity.created(registrationLocation).contentType(MediaType.APPLICATION_JSON).body(registration);
	}

	@PutMapping(value = "/registration/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Registration> updateRegistration(@PathVariable Long id, @RequestBody Request request) {
		Registration registration = registrationService.updateRegistration(id, request.getPhoneNumber());
		logger.info("Updated registration: {}", registration);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registration);
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
	@AllArgsConstructor
	@NoArgsConstructor
	static class Request {

		@JsonProperty("agent_name")
		private String agentName;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

}
