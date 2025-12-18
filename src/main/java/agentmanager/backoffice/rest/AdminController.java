package agentmanager.backoffice.rest;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/admin")
public class AdminController {

	private final Logger logger = LogManager.getLogger(AdminController.class);

	@Autowired
	private SaleExecutiveService saleExecutiveService;

	@Autowired
	private RegistrationService registrationService;

	@GetMapping("/sale_executive/list")
	public ResponseEntity<List<SaleExecutiveResponse>> getSaleExecutives() {
		List<SaleExecutive> saleExecutives = saleExecutiveService.getSaleExecutives();
		List<SaleExecutiveResponse> response = saleExecutives.stream()
				.map(saleExecutive -> new SaleExecutiveResponse(saleExecutive)).collect(Collectors.toList());
		logger.info("Fetched sale executives: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping(value = "/sale_executive/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaleExecutiveResponse> addSaleExecutive(@RequestBody SaleExecutiveRequest request) {
		SaleExecutive saleExecutive = saleExecutiveService.addSaleExecutive(request.getUsername(), request.getEmail(),
				request.getPassword(), request.getPhoneNumber());
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive);
		URI saleExecutiveLocation = generateEntryUri(saleExecutive.getId());
		logger.info("Sale Executive: {} created at: {}", response, saleExecutiveLocation);

		return ResponseEntity.created(saleExecutiveLocation).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("/sale_executive/details/{id}")
	public ResponseEntity<SaleExecutiveResponse> getSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(id);
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive);
		logger.info("Fetched sale executive: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PutMapping(value = "/sale_executive/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaleExecutiveResponse> updateSaleExecutiveJson(@PathVariable Long id,
			@RequestBody SaleExecutiveRequest request) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		SaleExecutive saleExecutive = saleExecutiveService.updateSaleExecutive(id, request.getEmail(),
				request.getPhoneNumber());
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive);

		logger.info("Updated admin: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@DeleteMapping("/sale_executive/delete/{id}")
	public ResponseEntity<Void> deleteSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		saleExecutiveService.deleteSaleExecutive(id);
		logger.info("Sale executive with id {} is deleted", id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/registration/list")
	public ResponseEntity<List<RegistrationResponse>> getRegistrations() {
		List<Registration> registrations = registrationService.getRegistrations();
		List<RegistrationResponse> response = registrations.stream()
				.map(registration -> new RegistrationResponse(registration)).collect(Collectors.toList());
		logger.info("Fetched registrations: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("/registration/details/{id}")
	public ResponseEntity<RegistrationResponse> getRegistration(@PathVariable Long id) {
		Registration registration = registrationService.getRegistration(id);
		RegistrationResponse response = new RegistrationResponse(registration);
		logger.info("Fetched registration: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PutMapping("/registration/update/{id}")
	public ResponseEntity<RegistrationResponse> updateRegistration(@PathVariable Long id,
			@RequestBody RegistrationRequest request) {
		Registration registrationFetched = registrationService.getRegistration(id);
		if (registrationFetched == null)
			throw new IllegalArgumentException("No such registration with id: " + id);

		Registration registration = registrationService.updateRegistration(id, request.getPhoneNumber());
		RegistrationResponse response = new RegistrationResponse(registration);
		logger.info("Updated registration: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class SaleExecutiveRequest {

		private String username;

		private String email;

		private String password;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

	@Data
	@NoArgsConstructor
	static class SaleExecutiveResponse {

		private Long id;

		private String username;

		private String email;

		@JsonProperty("phone_number")
		private String phoneNumber;

		SaleExecutiveResponse(SaleExecutive saleExecutive) {
			this.id = saleExecutive.getId();
			this.username = saleExecutive.getUsername();
			this.email = saleExecutive.getEmail();
			this.phoneNumber = saleExecutive.getPhoneNumber();
		}

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class RegistrationRequest {

		@JsonProperty("agent_name")
		private String agentName;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

	@Data
	@NoArgsConstructor
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

		RegistrationResponse(Registration registration) {
			this.id = registration.getId();
			this.agentName = registration.getAgentName();
			this.phoneNumber = registration.getPhoneNumber();
			this.registeredAt = registration.getRegisteredAt();
			this.registeredBy = registration.getSaleExecutive().getUsername();
		}

	}

}
