package agent_manager.back_office;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends CrudRepository<Admin, Long> {

}
