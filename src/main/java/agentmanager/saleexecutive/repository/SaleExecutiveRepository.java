package agentmanager.saleexecutive.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import agentmanager.saleexecutive.model.SaleExecutive;

@Repository
public interface SaleExecutiveRepository extends CrudRepository<SaleExecutive, Long> {

}
