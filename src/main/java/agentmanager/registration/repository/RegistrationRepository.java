package agentmanager.registration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

	Page<Registration> findBySaleExecutive(SaleExecutive saleExecutive, Pageable pageable);

}
