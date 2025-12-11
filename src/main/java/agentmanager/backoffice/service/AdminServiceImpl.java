package agentmanager.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;

@Service
@Transactional
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
		return adminOptional.orElse(null);
	}

	@Override
	public Admin addAdmin(Admin admin) {
		admin.encodePassword(passwordEncoder);
		Admin adminAdded = adminRepository.save(admin);
		return adminAdded;
	}

	@Override
	public Admin updateAdmin(Long id, Admin admin) {
		Admin adminToUpdate = admin.toBuilder().id(id).build();
		adminToUpdate.encodePassword(passwordEncoder);
		Admin adminUpdated = adminRepository.save(adminToUpdate);
		return adminUpdated;
	}

	@Override
	public void deleteAdmin(Long id) {
		adminRepository.deleteById(id);
	}

}
