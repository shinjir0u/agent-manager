package agentmanager.backoffice.service;

import java.util.List;

import agentmanager.backoffice.model.Admin;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface AdminService {
	List<Admin> getAdmins(int page, int size);

	Admin getAdmin(Long id);

	Admin addAdmin(String username, String email, String password);

	Admin updateAdmin(Long id, String email);

	void deleteAdmin(Long id);

	SaleExecutive terminateSaleExecutive(Long saleExecutiveId);

	SaleExecutive reassignRegistrationsToNewSaleExecutive(Long saleExecutiveId, Long newSaleExecutiveId);
}
