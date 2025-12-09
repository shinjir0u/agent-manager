package agent_manager.sale_executive;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleExecutiveRepository extends CrudRepository<SaleExecutive, Long> {

}
