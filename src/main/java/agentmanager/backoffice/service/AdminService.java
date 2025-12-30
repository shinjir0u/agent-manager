package agentmanager.backoffice.service;

import org.springframework.data.domain.Page;

import agentmanager.backoffice.model.Admin;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface AdminService {
	Page<Admin> getAdmins(int page, int size, String username, String email);

	Admin getAdmin(Long id);

	Admin addAdmin(String username, String email, String password);

	Admin updateAdmin(Long id, String email);

	void deleteAdmin(Long id);

	SaleExecutive terminateSaleExecutive(Long saleExecutiveId);

	SaleExecutive reassignRegistrationsToNewSaleExecutive(Long saleExecutiveId, Long newSaleExecutiveId);
}
