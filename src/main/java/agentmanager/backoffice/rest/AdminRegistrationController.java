package agentmanager.backoffice.rest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import agentmanager.registration.model.Registration;
import agentmanager.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/admin/registration")
public class AdminRegistrationController {

	private final Logger logger = LogManager.getLogger(AdminRegistrationController.class);

	@Autowired
	private RegistrationService registrationService;

	@GetMapping("/list")
	public ResponseEntity<List<RegistrationResponse>> getRegistrations(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, RegistrationParams registrationParams) {
		List<Registration> registrations = registrationService.getRegistrations(page, size,
				registrationParams.getAgent_name(), registrationParams.getPhone_number(),
				registrationParams.getRegistered_at(), registrationParams.getRegistered_by());
		List<RegistrationResponse> response = registrations.stream()
				.map(registration -> new RegistrationResponse(registration.getId(), registration.getAgentName(),
						registration.getPhoneNumber(), registration.getRegisteredAt(),
						registration.getSaleExecutive().getUsername()))
				.collect(Collectors.toList());
		logger.info("Fetched registrations: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<RegistrationResponse> getRegistration(@PathVariable Long id) {
		Registration registration = registrationService.getRegistration(id);
		RegistrationResponse response = new RegistrationResponse(registration.getId(), registration.getAgentName(),
				registration.getPhoneNumber(), registration.getRegisteredAt(),
				registration.getSaleExecutive().getUsername());
		logger.info("Fetched registration: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<RegistrationResponse> updateRegistration(@PathVariable Long id,
			@RequestBody RegistrationUpdateRequest request) {
		Registration registrationFetched = registrationService.getRegistration(id);
		if (registrationFetched == null)
			throw new IllegalArgumentException("No such registration with id: " + id);

		Registration registration = registrationService.updateRegistration(id, request.getPhoneNumber());
		RegistrationResponse response = new RegistrationResponse(registration.getId(), registration.getAgentName(),
				registration.getPhoneNumber(), registration.getRegisteredAt(),
				registration.getSaleExecutive().getUsername());
		logger.info("Updated registration: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class RegistrationUpdateRequest {

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

		private Long registered_by;

	}

}
