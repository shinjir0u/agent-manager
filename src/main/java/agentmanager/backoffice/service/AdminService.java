package agentmanager.backoffice.service;

import java.util.List;

import agentmanager.backoffice.model.Admin;

public interface AdminService {
	List<Admin> getAdmins();

	Admin getAdmin(Long id);

	Admin addAdmin(Admin admin);

	Admin updateAdmin(Long id, Admin admin);

	void deleteAdmin(Long id);
}
