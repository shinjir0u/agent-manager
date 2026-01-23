package agentmanager.common;

import org.springframework.stereotype.Service;

import agentmanager.common.model.token.Token;
import agentmanager.common.repository.TokenRepository;
import agentmanager.common.service.TokenService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

	private final TokenRepository tokenRepository;

	@Override
	public Token getTokenByValue(String value) {
		Token token = tokenRepository.findByValue(value).orElse(null);
		return token;
	}

}
