package agentmanager.backoffice.service;

import org.springframework.data.domain.Page;

import agentmanager.backoffice.model.Admin;
import agentmanager.common.model.token.Token;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface AdminService {
	Token login(String username, String password);

	Page<Admin> getAdmins(int page, int size, String sort, String direction, String username, String email);

	Admin getAdmin(Long id);

	Admin addAdmin(String username, String email, String password);

	Admin updateAdmin(Long id, String email);

	void deleteAdmin(Long id);

	SaleExecutive terminateSaleExecutiveAndTransferRegistrations(Long saleExecutiveId, Long newSaleExecutiveId);
}
