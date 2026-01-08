package agentmanager.common.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.stereotype.Service;

import agentmanager.common.model.Token;

@Service
public class TokenService {

	public Token generateToken(Long id) {
		String tokenPrefix = UUID.randomUUID().toString();
		Long expirationDate = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli();

		String token = tokenPrefix + "." + id + "." + expirationDate;
		return new Token(token);
	}

}