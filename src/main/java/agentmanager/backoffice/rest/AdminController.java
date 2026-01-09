package agentmanager.backoffice.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agentmanager.backoffice.service.AdminService;
import agentmanager.common.model.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/login")
	public ResponseEntity<Token> login(@RequestBody LoginRequest loginRequest) {
		Token token = adminService.login(loginRequest.getUsername(), loginRequest.getPassword());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(token);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class LoginRequest {

		private String username;

		private String password;

	}

}
