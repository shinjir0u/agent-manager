package agentmanager.registration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import agentmanager.registration.model.Registration;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {

}
