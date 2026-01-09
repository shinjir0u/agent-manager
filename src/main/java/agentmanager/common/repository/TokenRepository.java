package agentmanager.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import agentmanager.common.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

}
