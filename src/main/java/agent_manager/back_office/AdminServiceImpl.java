package agent_manager.back_office;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<Admin> getAdmins() {
		List<Admin> admins = new ArrayList<>();
		adminRepository.findAll().forEach(admins::add);
		return admins;
	}

	@Override
	public Admin getAdmin(Long id) {
		Optional<Admin> adminOptional = adminRepository.findById(id);
		if (!adminOptional.isPresent())
			throw new IllegalArgumentException("No such admin with id: " + id);
		return adminOptional.get();
	}

	@Override
	public Admin addAdmin(Admin admin) {
		String hashedPassword = passwordEncoder.encode(admin.getPassword());
		Admin adminToAdd = admin.toBuilder().password(hashedPassword).build();
		adminRepository.save(adminToAdd);
		return adminToAdd;
	}

	@Override
	public Admin updateAdmin(Long id, Admin admin) {
		Admin adminToAdd = admin.toBuilder().id(id).build();
		adminRepository.save(adminToAdd);
		return adminToAdd;
	}

	@Override
	public void deleteAdmin(Long id) {
		adminRepository.deleteById(id);
	}

}
