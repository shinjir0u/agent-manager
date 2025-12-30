package agentmanager.saleexecutive.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agentmanager.saleexecutive.model.SaleExecutive;

@Repository
public interface SaleExecutiveRepository extends JpaRepository<SaleExecutive, Long> {

	Page<SaleExecutive> findAll(Specification<SaleExecutive> specification, Pageable pageable);

	Optional<SaleExecutive> findByUsername(String username);

}
