package agentmanager.backoffice.service;

import java.util.List;

import agentmanager.backoffice.model.Admin;

public interface AdminService {
	List<Admin> getAdmins();

	Admin getAdmin(Long id);

	Admin addAdmin(String username, String email, String password);

	Admin updateAdmin(Long id, String email);

	void deleteAdmin(Long id);
}
