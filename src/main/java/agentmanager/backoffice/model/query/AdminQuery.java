package agentmanager.backoffice.model.query;

import org.springframework.data.domain.Page;

import agentmanager.backoffice.model.Admin;

public interface AdminQuery {

	Page<Admin> getAdmins(Integer page, Integer size, String sort, String direction, String username, String email);

}
