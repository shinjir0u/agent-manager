package agentmanager.backoffice.rest;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import agentmanager.backoffice.service.AdminService;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;
import agentmanager.saleexecutive.service.SaleExecutiveService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/admin/sale_executive")
public class AdminSaleExecutiveController {

	private final Logger logger = LogManager.getLogger(AdminSaleExecutiveController.class);

	@Autowired
	private SaleExecutiveService saleExecutiveService;

	@Autowired
	private AdminService adminService;

	@GetMapping("/list")
	public ResponseEntity<List<SaleExecutiveResponse>> getSaleExecutives(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, SaleExecutiveParams saleExecutiveParams) {
		List<SaleExecutive> saleExecutives = saleExecutiveService.getSaleExecutives(page, size,
				saleExecutiveParams.getUsername(), saleExecutiveParams.getEmail(),
				saleExecutiveParams.getPhone_number(), saleExecutiveParams.getStatus());
		List<SaleExecutiveResponse> response = saleExecutives
				.stream().map(saleExecutive -> new SaleExecutiveResponse(saleExecutive.getId(),
						saleExecutive.getUsername(), saleExecutive.getEmail(), saleExecutive.getPhoneNumber()))
				.collect(Collectors.toList());
		logger.info("Fetched sale executives: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<SaleExecutiveResponse> getSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(id);
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive.getId(), saleExecutive.getUsername(),
				saleExecutive.getEmail(), saleExecutive.getPhoneNumber());
		logger.info("Fetched sale executive: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PostMapping("/register")
	public ResponseEntity<SaleExecutiveResponse> addSaleExecutive(@RequestBody SaleExecutiveCreateRequest request) {
		SaleExecutive saleExecutive = saleExecutiveService.addSaleExecutive(request.getUsername(), request.getEmail(),
				request.getPassword(), request.getPhoneNumber());
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive.getId(), saleExecutive.getUsername(),
				saleExecutive.getEmail(), saleExecutive.getPhoneNumber());
		URI saleExecutiveLocation = generateEntryUri(saleExecutive.getId());
		logger.info("Sale Executive: {} created at: {}", response, saleExecutiveLocation);

		return ResponseEntity.created(saleExecutiveLocation).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<SaleExecutiveResponse> updateSaleExecutiveJson(@PathVariable Long id,
			@RequestBody SaleExecutiveUpdateRequest request) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		SaleExecutive saleExecutive = saleExecutiveService.updateSaleExecutive(id, request.getEmail(),
				request.getPhoneNumber());
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive.getId(), saleExecutive.getUsername(),
				saleExecutive.getEmail(), saleExecutive.getPhoneNumber());

		logger.info("Updated admin: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteSaleExecutive(@PathVariable Long id) {
		SaleExecutive saleExecutiveFetched = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutiveFetched == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);

		saleExecutiveService.deleteSaleExecutive(id);
		logger.info("Sale executive with id {} is deleted", id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/reassign/{idToReassign}")
	public ResponseEntity<SaleExecutiveResponse> reassignAndTerminateSaleExecutive(@PathVariable Long id,
			@PathVariable Long idToReassign) {

		adminService.terminateSaleExecutive(id);
		SaleExecutive saleExecutive = adminService.reassignRegistrationsToNewSaleExecutive(id, idToReassign);
		SaleExecutiveResponse response = new SaleExecutiveResponse(saleExecutive.getId(), saleExecutive.getUsername(),
				saleExecutive.getEmail(), saleExecutive.getPhoneNumber());

		logger.info("Received sale executive: {}", response);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class SaleExecutiveCreateRequest {

		private String username;

		private String email;

		private String password;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class SaleExecutiveUpdateRequest {

		private String email;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class SaleExecutiveResponse {

		private Long id;

		private String username;

		private String email;

		@JsonProperty("phone_number")
		private String phoneNumber;

	}

	@Data
	@AllArgsConstructor
	static class SaleExecutiveParams {

		private String username;

		private String email;

		private String phone_number;

		private Status status;

	}

}
