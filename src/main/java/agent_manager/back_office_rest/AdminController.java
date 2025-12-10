package agent_manager.back_office_rest;

import java.net.URI;
import java.util.List;

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

import agent_manager.back_office.Admin;
import agent_manager.back_office.AdminService;

@RestController
@RequestMapping("/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping
	public ResponseEntity<List<Admin>> getAdmins() {
		List<Admin> admins = adminService.getAdmins();
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(admins);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> addAdminJson(@RequestBody Admin admin) {
		Admin adminAdded = adminService.addAdmin(admin);
		URI adminLocation = generateEntryUri(adminAdded);
		return ResponseEntity.created(adminLocation).contentType(MediaType.APPLICATION_JSON).body(adminAdded);
	}

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Admin> addAdminForm(Admin admin) {
		Admin adminAdded = adminService.addAdmin(admin);
		URI adminLocation = generateEntryUri(adminAdded);
		return ResponseEntity.created(adminLocation).contentType(MediaType.APPLICATION_JSON).body(adminAdded);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
		Admin admin = adminService.getAdmin(id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(admin);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> updateAdminJson(@PathVariable Long id, @RequestBody Admin admin) {
		Admin adminUpdated = adminService.updateAdmin(id, admin);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(adminUpdated);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Admin> updateAdminForm(@PathVariable Long id, Admin admin) {
		Admin adminUpdated = adminService.updateAdmin(id, admin);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(adminUpdated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) throws IllegalArgumentException {
		adminService.deleteAdmin(id);
		return ResponseEntity.noContent().build();
	}

	private URI generateEntryUri(Object entryId) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(entryId).toUri();
		return uri;
	}

}
