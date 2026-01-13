package agentmanager.common.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.common.model.Token;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {

	private final AdminRepository adminRepository;

	private final SaleExecutiveRepository saleExecutiveRepository;

	public Token generateToken(String id) {
		String tokenPrefix = UUID.randomUUID().toString();
		Long expirationDate = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli();

		String token = tokenPrefix + "." + id + "." + expirationDate;
		return new Token(token);
	}

	public Boolean validateToken(String token, String id) {
		if (id.startsWith("a")) {
			Long adminId = Long.valueOf(id.substring(1));
			Admin admin = adminRepository.findById(adminId).orElse(null);
			return admin.getToken().getValue().equals(token);
		} else if (id.startsWith("se")) {
			Long saleExecutiveId = Long.valueOf(id.substring(2));
			SaleExecutive saleExecutive = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);
			return saleExecutive.getToken().getValue().equals(token);
		}
		return false;
	}
}