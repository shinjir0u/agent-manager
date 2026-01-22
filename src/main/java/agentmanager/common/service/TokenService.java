package agentmanager.common.service;

import agentmanager.common.model.Token;

public interface TokenService {

	Token getTokenByValue(String value);

}
