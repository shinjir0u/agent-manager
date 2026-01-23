package agentmanager.backoffice.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agentmanager.backoffice.service.AdminService;
import agentmanager.common.model.request.LoginRequest;
import agentmanager.common.model.response.TokenResponse;
import agentmanager.common.model.token.Token;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
		Token token = adminService.login(request.getUsername(), request.getPassword());
		TokenResponse response = new TokenResponse(token.getValue());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}

}
