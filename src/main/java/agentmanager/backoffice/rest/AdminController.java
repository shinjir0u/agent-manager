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

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.service.SaleExecutiveService;

@RestController
@RequestMapping("/sale_executives")
public class AdminController {

	private final Logger logger = LogManager.getLogger(AdminController.class);

	@Autowired
	private SaleExecutiveService saleExecutiveService;

	@GetMapping
	public ResponseEntity<List<SaleExecutive>> getSaleExecutives() {
		List<SaleExecutive> saleExecutives = saleExecutiveService.getSaleExecutives();
		logger.info("Fetched sale executives: {}", saleExecutives);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutives);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaleExecutive> addSaleExecutiveJson(@RequestBody SaleExecutive saleExecutive) {
		SaleExecutive saleExecutiveAdded = saleExecutiveService.addSaleExecutive(saleExecutive);
		URI saleExecutiveLocation = generateEntryUri(saleExecutiveAdded.getId());
		logger.info("Sale Executive: {} created at: {}", saleExecutiveAdded, saleExecutiveLocation);
		return ResponseEntity.created(saleExecutiveLocation).contentType(MediaType.APPLICATION_JSON)
				.body(saleExecutiveAdded);
	}

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<SaleExecutive> addSaleExecutiveForm(SaleExecutive saleExecutive) {
		SaleExecutive saleExecutiveAdded = saleExecutiveService.addSaleExecutive(saleExecutive);
		URI saleExecutiveLocation = generateEntryUri(saleExecutiveAdded.getId());
		logger.info("Sale Executive: {} created at: {}", saleExecutiveAdded, saleExecutiveLocation);
		return ResponseEntity.created(saleExecutiveLocation).contentType(MediaType.APPLICATION_JSON)
				.body(saleExecutiveAdded);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SaleExecutive> getSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(id);
		logger.info("Fetched sale executive: {}", saleExecutive);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutive);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaleExecutive> updateSaleExecutiveJson(@PathVariable Long id,
			@RequestBody SaleExecutive saleExecutive) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		SaleExecutive saleExecutiveUpdated = saleExecutiveService.updateSaleExecutive(id, saleExecutive);
		logger.info("Updated admin: {}", saleExecutiveUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutiveUpdated);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<SaleExecutive> updateSaleExecutiveForm(@PathVariable Long id, SaleExecutive saleExecutive) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		SaleExecutive saleExecutiveUpdated = saleExecutiveService.updateSaleExecutive(id, saleExecutive);
		logger.info("Updated admin: {}", saleExecutiveUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saleExecutiveUpdated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		saleExecutiveService.deleteSaleExecutive(id);
		logger.info("Sale executive with id {} is deleted", id);
		return ResponseEntity.noContent().build();
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

}
