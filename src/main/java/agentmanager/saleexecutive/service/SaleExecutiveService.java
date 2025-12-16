package agentmanager.saleexecutive.service;

import java.util.List;

import agentmanager.saleexecutive.model.SaleExecutive;

public interface SaleExecutiveService {
	List<SaleExecutive> getSaleExecutives();

	SaleExecutive getSaleExecutive(Long id);

	SaleExecutive addSaleExecutive(String username, String email, String password, String phoneNumber);

	SaleExecutive updateSaleExecutive(Long id, String email, String phoneNumber);

	void deleteSaleExecutive(Long id);
}
