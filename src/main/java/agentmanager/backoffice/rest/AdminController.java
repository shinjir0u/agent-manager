package agentmanager.backoffice.rest;

import java.net.URI;
import java.util.List;

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
	public ResponseEntity<List<SaleExecutive>> getSaleExecutives() {
		List<SaleExecutive> saleExecutives = saleExecutiveService.getSaleExecutives();
		logger.info("Fetched sale executives: {}", saleExecutives);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutives);
	}

	@PostMapping(value = "/sale_executive/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaleExecutive> addSaleExecutive(@RequestBody Request request) {
		SaleExecutive saleExecutiveAdded = saleExecutiveService.addSaleExecutive(request.getUsername(),
				request.getEmail(), request.getPassword(), request.getPhoneNumber());
		URI saleExecutiveLocation = generateEntryUri(saleExecutiveAdded.getId());
		logger.info("Sale Executive: {} created at: {}", saleExecutiveAdded, saleExecutiveLocation);
		return ResponseEntity.created(saleExecutiveLocation).contentType(MediaType.APPLICATION_JSON)
				.body(saleExecutiveAdded);
	}

	@PostMapping(value = "/sale_executive/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<SaleExecutive> addSaleExecutiveForm(Request request) {
		SaleExecutive saleExecutiveAdded = saleExecutiveService.addSaleExecutive(request.getUsername(),
				request.getEmail(), request.getPassword(), request.getPhoneNumber());
		URI saleExecutiveLocation = generateEntryUri(saleExecutiveAdded.getId());
		logger.info("Sale Executive: {} created at: {}", saleExecutiveAdded, saleExecutiveLocation);
		return ResponseEntity.created(saleExecutiveLocation).contentType(MediaType.APPLICATION_JSON)
				.body(saleExecutiveAdded);
	}

	@GetMapping("/sale_executive/details/{id}")
	public ResponseEntity<SaleExecutive> getSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(id);
		logger.info("Fetched sale executive: {}", saleExecutive);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutive);
	}

	@PutMapping(value = "/sale_executive/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaleExecutive> updateSaleExecutiveJson(@PathVariable Long id, @RequestBody Request request) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		SaleExecutive saleExecutiveUpdated = saleExecutiveService.updateSaleExecutive(id, request.getEmail(),
				request.getPhoneNumber());
		logger.info("Updated admin: {}", saleExecutiveUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutiveUpdated);
	}

	@PutMapping(value = "/sale_executive/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<SaleExecutive> updateSaleExecutiveForm(@PathVariable Long id, Request request) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		SaleExecutive saleExecutiveUpdated = saleExecutiveService.updateSaleExecutive(id, request.getEmail(),
				request.getPhoneNumber());
		logger.info("Updated admin: {}", saleExecutiveUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutiveUpdated);
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
	public ResponseEntity<List<Registration>> getRegistrations() {
		List<Registration> registrations = registrationService.getRegistrations();
		logger.info("Fetched registrations: {}", registrations);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrations);
	}

	@GetMapping("/registration/details/{id}")
	public ResponseEntity<Registration> getRegistration(@PathVariable Long id) {
		Registration registration = registrationService.getRegistration(id);
		logger.info("Fetched registration: {}", registration);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registration);
	}

	@PutMapping("/registration/update/{id}")
	public ResponseEntity<Registration> updateRegistration(@PathVariable Long id, @RequestBody Request request) {
		Registration registrationFetched = registrationService.getRegistration(id);
		if (registrationFetched == null)
			throw new IllegalArgumentException("No such registration with id: " + id);

		Registration registrationUpdated = registrationService.updateRegistration(id, request.getPhoneNumber());
		logger.info("Updated registration: {}", registrationUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrationUpdated);
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {

		private String username;

		private String email;

		private String password;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

}
