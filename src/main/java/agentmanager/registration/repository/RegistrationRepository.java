package agentmanager.registration.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Long> {

	Iterable<Registration> findBySaleExecutive(SaleExecutive saleExecutive);

	Optional<Registration> findBySaleExecutiveAndId(SaleExecutive saleExecutive, Long id);

	void deleteBySaleExecutiveAndId(SaleExecutive saleExecutive, Long id);

}
