package agentmanager.common.service.token;

import agentmanager.common.model.token.Token;

public interface TokenService {

	Token getTokenByValue(String value);

}
