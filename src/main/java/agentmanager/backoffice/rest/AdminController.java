package agentmanager.backoffice.rest;

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
	public Token login(@RequestBody LoginRequest loginRequest) {
		return adminService.login(loginRequest.getUsername(), loginRequest.getPassword());
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class LoginRequest {

		private String username;

		private String password;

	}

}
