package agentmanager.saleexecutive.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import agentmanager.common.model.Token;
import agentmanager.common.repository.TokenRepository;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.SaleExecutiveStatus;
import agentmanager.saleexecutive.query.SaleExecutiveQuery;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SaleExecutiveServiceImpl implements SaleExecutiveService {

	private final SaleExecutiveRepository saleExecutiveRepository;

	private final SaleExecutiveQuery saleExecutiveQuery;

	private final TokenRepository tokenRepository;

	@Override
	public Token login(String username, String password) {
		SaleExecutive saleExecutive = saleExecutiveRepository.findByUsername(username)
				.orElseThrow(() -> new BadCredentialsException("No such sale executive with username: " + username));
		if (!saleExecutive.validatePassword(password))
			throw new BadCredentialsException("Invalid password");

		Token token = saleExecutive.getToken();

		if (token == null || token.isTokenExpired()) {
//			token.generateToken();
			saleExecutive.setToken(token);

			if (token.getId() == null)
				tokenRepository.save(token);
			saleExecutiveRepository.save(saleExecutive);
		}
		return token;
	}

	@Override
	public Page<SaleExecutive> getSaleExecutives(Integer page, Integer size, String sort, String direction,
			String username, String email, String phoneNumber, SaleExecutiveStatus status) {
		Page<SaleExecutive> saleExecutives = saleExecutiveQuery.getSaleExecutives(page, size, sort, direction, username,
				email, phoneNumber, status);
		return saleExecutives;
	}

	@Override
	public SaleExecutive getSaleExecutive(Long id) {
		Optional<SaleExecutive> saleExecutiveOptional = saleExecutiveRepository.findById(id);
		return saleExecutiveOptional.orElse(null);
	}

	@Override
	public SaleExecutive addSaleExecutive(String username, String email, String password, String phoneNumber) {
		SaleExecutive saleExecutiveToAdd = new SaleExecutive(username, email, password, phoneNumber);
		SaleExecutive saleExecutiveAdded = saleExecutiveRepository.save(saleExecutiveToAdd);
		return saleExecutiveAdded;
	}

	@Override
	public SaleExecutive updateSaleExecutive(Long id, String email, String phoneNumber) {
		SaleExecutive saleExecutiveFetched = getSaleExecutive(id);
		saleExecutiveFetched.update(email, phoneNumber);
		SaleExecutive saleExecutiveUpdated = saleExecutiveRepository.save(saleExecutiveFetched);
		return saleExecutiveUpdated;
	}

	@Override
	public void deleteSaleExecutive(Long id) {
		saleExecutiveRepository.deleteById(id);
	}

}
