package agentmanager.backoffice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private SaleExecutiveRepository saleExecutiveRepository;

	@Autowired
	private RegistrationRepository registrationRepository;

	@Override
	public List<Admin> getAdmins(int page, int size) {
		Page<Admin> admins = adminRepository.findAll(PageRequest.of(page, size));
		return admins.getContent();
	}

	@Override
	public Admin getAdmin(Long id) {
		Optional<Admin> adminOptional = adminRepository.findById(id);
		return adminOptional.orElse(null);
	}

	@Override
	public Admin addAdmin(String username, String email, String password) {

		Admin admin = new Admin(username, email, password);

		Admin adminAdded = adminRepository.save(admin);
		return adminAdded;
	}

	@Override
	public Admin updateAdmin(Long id, String email) {
		Admin adminFetched = getAdmin(id);

		adminFetched.update(email);

		Admin adminUpdated = adminRepository.save(adminFetched);
		return adminUpdated;
	}

	@Override
	public void deleteAdmin(Long id) {
		adminRepository.deleteById(id);
	}

	@Override
	public SaleExecutive terminateSaleExecutive(Long saleExecutiveId) {
		SaleExecutive saleExecutive = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);

		saleExecutive.terminate();
		SaleExecutive saleExecutiveTerminated = saleExecutiveRepository.save(saleExecutive);
		saleExecutiveRepository.save(saleExecutiveTerminated);
		return saleExecutiveTerminated;
	}

	@Override
	public SaleExecutive reassignRegistrationsToNewSaleExecutive(Long saleExecutiveId, Long newSaleExecutiveId) {
		SaleExecutive saleExecutiveToTransfer = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);
		SaleExecutive saleExecutiveToReceive = saleExecutiveRepository.findById(newSaleExecutiveId).orElse(null);

		saleExecutiveToTransfer.transferRegistrations(saleExecutiveToReceive);
		for (Registration registration : saleExecutiveToReceive.getRegistrations())
			registrationRepository.save(registration);
		saleExecutiveRepository.save(saleExecutiveToReceive);
		saleExecutiveRepository.save(saleExecutiveToTransfer);

		return saleExecutiveToReceive;
	}

}
