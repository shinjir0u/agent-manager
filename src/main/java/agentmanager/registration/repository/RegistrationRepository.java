package agentmanager.registration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

@Repository
public interface RegistrationRepository
		extends JpaRepository<Registration, Long>, JpaSpecificationExecutor<Registration> {

	Page<Registration> findAll(Specification<Registration> specification, Pageable pageable);

	Page<Registration> findBySaleExecutive(SaleExecutive saleExecutive, Specification<Registration> specification,
			Pageable pageable);

}
