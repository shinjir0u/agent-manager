package agentmanager.saleexecutive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agentmanager.saleexecutive.model.SaleExecutive;

@Repository
public interface SaleExecutiveRepository extends JpaRepository<SaleExecutive, Long> {

}
