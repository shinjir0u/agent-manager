package agentmanager.common.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import agentmanager.common.model.token.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

	Optional<Token> findByValue(String value);

}
