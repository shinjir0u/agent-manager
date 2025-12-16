package agentmanager.saleexecutive.rest;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import agentmanager.registration.service.RegistrationService;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.service.SaleExecutiveService;

@RestController
@RequestMapping("/sale_executives/{saleExecutiveId}/registrations")
public class SaleExecutiveController {

	private final Logger logger = LogManager.getLogger(SaleExecutiveController.class);

	private final RegistrationService registrationService;

	private final SaleExecutiveService saleExecutiveService;

	public SaleExecutiveController(RegistrationService registrationService, SaleExecutiveService saleExecutiveService) {
		this.registrationService = registrationService;
		this.saleExecutiveService = saleExecutiveService;
	}

//	@GetMapping
//	public ResponseEntity<List<Registration>> getRegistrations(@PathVariable Long saleExecutiveId) {
//		List<Registration> registrations = registrationService.getRegistrations(saleExecutiveId);
//
//		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrations);
//	}
//
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Registration> createRegistrationJson(@PathVariable Long saleExecutiveId,
//			@RequestBody Registration registration) {
//		Registration registrationAdded = registrationService.addRegistration(saleExecutiveId, registration);
//
//		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrationAdded);
//	}

//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Registration> createRegistrationForm(@PathVariable Long saleExecutiveId,
//			Registration registration) {
//		Registration registrationAdded = registrationService.addRegistration(saleExecutiveId, registration);
//
//		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(registrationAdded);
//	}

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

}
