package agentmanager.backoffice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import agentmanager.backoffice.model.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {

}
