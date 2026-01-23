package agentmanager.backoffice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.model.query.AdminQuery;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.common.model.Token;
import agentmanager.common.repository.TokenRepository;
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

	@Override
	public Token login(String username, String password) {
		Admin admin = adminRepository.findByUsername(username)
				.orElseThrow(() -> new BadCredentialsException("No admin with username: " + username));
		if (!admin.validatePassword(password))
			throw new BadCredentialsException("Invalid password");

		Token token = admin.getToken();

		if (token == null || token.isTokenExpired()) {
//			token.generateToken();
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
	public SaleExecutive terminateSaleExecutiveAndTransferRegistrations(Long saleExecutiveId, Long newSaleExecutiveId) {
		SaleExecutive saleExecutiveToTerminate = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);
		SaleExecutive saleExecutiveToReceive = saleExecutiveRepository.findById(newSaleExecutiveId).orElse(null);

		saleExecutiveToTerminate.terminate();
		saleExecutiveToTerminate.transferRegistrations(saleExecutiveToReceive);
		SaleExecutive saleExecutiveTerminated = saleExecutiveRepository.save(saleExecutiveToTerminate);
		SaleExecutive saleExecutive = saleExecutiveRepository.save(saleExecutiveToReceive);
		return saleExecutive;
	}

}
