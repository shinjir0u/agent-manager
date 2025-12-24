package agentmanager.saleexecutive.service;

import java.util.List;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;

public interface SaleExecutiveService {
	List<SaleExecutive> getSaleExecutives(int page, int size, String username, String email, String phoneNumber,
			Status status);

	SaleExecutive getSaleExecutive(Long id);

	SaleExecutive addSaleExecutive(String username, String email, String password, String phoneNumber);

	SaleExecutive updateSaleExecutive(Long id, String email, String phoneNumber);

	void deleteSaleExecutive(Long id);
}
