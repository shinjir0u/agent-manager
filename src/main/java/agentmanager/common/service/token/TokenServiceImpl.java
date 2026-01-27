package agentmanager.common.service.token;

import org.springframework.stereotype.Service;

import agentmanager.common.model.token.Token;
import agentmanager.common.repository.TokenRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

	private final TokenRepository tokenRepository;

	@Override
	public Token getTokenByValue(String value) {
		Token token = tokenRepository.findByValue(value)
				.orElseThrow(() -> new IllegalArgumentException("No such token with value: " + value));
		return token;
	}

}
