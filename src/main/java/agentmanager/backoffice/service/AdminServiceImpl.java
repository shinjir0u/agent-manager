package agentmanager.backoffice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.backoffice.repository.specification.AdminSpecifications;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminRepository adminRepository;

	private final SaleExecutiveRepository saleExecutiveRepository;

	@Override
	public List<Admin> getAdmins(int page, int size, String username, String email) {
		Specification<Admin> specification = Specification.where(AdminSpecifications.withUsername(username))
				.and(AdminSpecifications.withEmail(email));
		Page<Admin> admins = adminRepository.findAll(specification, PageRequest.of(page, size));
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
		return saleExecutiveTerminated;
	}

	@Override
	public SaleExecutive reassignRegistrationsToNewSaleExecutive(Long saleExecutiveId, Long newSaleExecutiveId) {
		SaleExecutive saleExecutiveToTransfer = saleExecutiveRepository.findById(saleExecutiveId).orElse(null);
		SaleExecutive saleExecutiveToReceive = saleExecutiveRepository.findById(newSaleExecutiveId).orElse(null);

		saleExecutiveToTransfer.transferRegistrations(saleExecutiveToReceive);
		SaleExecutive saleExecutive = saleExecutiveRepository.save(saleExecutiveToReceive);
		return saleExecutive;
	}

}
