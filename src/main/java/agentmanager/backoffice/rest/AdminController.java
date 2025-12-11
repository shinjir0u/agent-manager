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

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.service.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

	private final Logger logger = LogManager.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@GetMapping
	public ResponseEntity<List<Admin>> getAdmins() {
		List<Admin> admins = adminService.getAdmins();
		logger.info("Fetched admins: {} ", admins);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(admins);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> addAdminJson(@RequestBody Admin admin) {
		Admin adminAdded = adminService.addAdmin(admin);
		URI adminLocation = generateEntryUri(adminAdded.getId());
		logger.info("Admin: {} created at: {}", adminAdded, adminLocation);
		return ResponseEntity.created(adminLocation).contentType(MediaType.APPLICATION_JSON).body(adminAdded);
	}

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Admin> addAdminForm(Admin admin) {
		Admin adminAdded = adminService.addAdmin(admin);
		URI adminLocation = generateEntryUri(adminAdded.getId());
		logger.info("Admin {} created at {}", adminAdded, adminLocation);
		return ResponseEntity.created(adminLocation).contentType(MediaType.APPLICATION_JSON).body(adminAdded);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
		Admin admin = adminService.getAdmin(id);
		logger.info("Fetched admin: {}", admin);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(admin);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> updateAdminJson(@PathVariable Long id, @RequestBody Admin admin) {
		Admin adminFetched = adminService.getAdmin(id);
		if (adminFetched == null)
			throw new IllegalArgumentException("No such admin with id: " + id);

		Admin adminUpdated = adminService.updateAdmin(id, admin);
		logger.info("Updated admin: {}", adminUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(adminUpdated);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Admin> updateAdminForm(@PathVariable Long id, Admin admin) {
		Admin adminFetched = adminService.getAdmin(id);
		if (adminFetched == null)
			throw new IllegalArgumentException("No such admin with id: " + id);

		Admin adminUpdated = adminService.updateAdmin(id, admin);
		logger.info("Updated admin: {}", adminUpdated);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(adminUpdated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) throws IllegalArgumentException {
		Admin adminFetched = adminService.getAdmin(id);
		if (adminFetched == null)
			throw new IllegalArgumentException("No such admin with id: " + id);

		adminService.deleteAdmin(id);
		logger.info("Admin with id {} is deleted", id);
		return ResponseEntity.noContent().build();
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

}
