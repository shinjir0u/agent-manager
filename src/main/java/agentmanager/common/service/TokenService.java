package agentmanager.common.service;

import agentmanager.common.model.token.Token;

public interface TokenService {

	Token getTokenByValue(String value);

}
