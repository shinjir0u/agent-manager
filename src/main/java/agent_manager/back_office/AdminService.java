package agent_manager.back_office;

import java.util.List;

public interface AdminService {
	List<Admin> getAdmins();

	Admin getAdmin(Long id);

	Admin addAdmin(Admin admin);

	Admin updateAdmin(Long id, Admin admin);

	void deleteAdmin(Long id);
}
