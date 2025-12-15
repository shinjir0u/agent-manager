package agentmanager.saleexecutive.rest;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.service.SaleExecutiveService;

@RestController
@RequestMapping("/sale_executives")
public class SaleExecutiveController {

	private final Logger logger = LogManager.getLogger(SaleExecutiveController.class);

	private SaleExecutiveService saleExecutiveService;

	public SaleExecutiveController(SaleExecutiveService saleExecutiveService) {
		this.saleExecutiveService = saleExecutiveService;
	}

	@PostMapping(value = "/{id}/registrations", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Registration> addRegistrationJson(@PathVariable Long id,
			@RequestBody Registration registration) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		Registration registrationAdded = saleExecutiveService.addAgent(id, registration);
		logger.info("Registration {} created by sale executive with id: {}", registrationAdded, id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrationAdded);
	}

	@PostMapping(value = "/{id}/registrations", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Registration> addRegistrationForm(@PathVariable Long id, Registration registration) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		Registration registrationAdded = saleExecutiveService.addAgent(id, registration);
		logger.info("Registration {} created by sale executive with id: {}", registrationAdded, id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrationAdded);
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

}
