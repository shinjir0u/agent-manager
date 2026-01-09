package agentmanager.backoffice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.model.query.AdminQuery;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.common.model.Token;
import agentmanager.common.repository.TokenRepository;
import agentmanager.common.service.TokenService;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;

	private final TokenRepository tokenRepository;

	private final SaleExecutiveRepository saleExecutiveRepository;

	private final AdminQuery adminQuery;

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	@Override
	public Token login(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (!authentication.isAuthenticated())
			throw new UsernameNotFoundException("Invalid credentials.");

		Admin admin = adminRepository.findByUsername(username).orElse(null);
		Token token = admin.getToken();

		if (token == null || token.isTokenExpired()) {
			token = tokenService.generateToken("a" + admin.getId());
			admin.setToken(token);

			if (token.getId() == null)
				tokenRepository.save(token);
			adminRepository.save(admin);
		}
		return token;
	}

	@Override
	public Page<Admin> getAdmins(int page, int size, String sort, String direction, String username, String email) {
		Page<Admin> admins = adminQuery.getAdmins(page, size, sort, direction, username, email);
		return admins;
	}

	@Override
	public Admin getAdmin(Long id) {
		Optional<Admin> adminOptional = adminRepository.findById(id);
		return adminOptional.orElse(null);
	}

	@Override
	public Admin addAdmin(String username, String email, String password) {

		Admin admin = new Admin(username, email, password);

		Admin adminAdded = adminRepository.save(admin);
		return adminAdded;
	}

	@Override
	public Admin updateAdmin(Long id, String email) {
		Admin adminFetched = getAdmin(id);

		adminFetched.update(email);

		Admin adminUpdated = adminRepository.save(adminFetched);
		return adminUpdated;
	}

	@Override
	public void deleteAdmin(Long id) {
		adminRepository.deleteById(id);
	}

	@Override
	public SaleExecutive terminateSaleExecutive(Long saleExecutiveId) {
		SaleExecutive saleExecutive = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);

		saleExecutive.terminate();
		SaleExecutive saleExecutiveTerminated = saleExecutiveRepository.save(saleExecutive);
		return saleExecutiveTerminated;
	}

	@Override
	public SaleExecutive reassignRegistrationsToNewSaleExecutive(Long saleExecutiveId, Long newSaleExecutiveId) {
		SaleExecutive saleExecutiveToTransfer = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);
		SaleExecutive saleExecutiveToReceive = saleExecutiveRepository.findById(newSaleExecutiveId).orElse(null);

		saleExecutiveToTransfer.transferRegistrations(saleExecutiveToReceive);
		SaleExecutive saleExecutive = saleExecutiveRepository.save(saleExecutiveToReceive);
		return saleExecutive;
	}

}
