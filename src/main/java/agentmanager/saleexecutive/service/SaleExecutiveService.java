package agentmanager.saleexecutive.service;

import org.springframework.data.domain.Page;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;

public interface SaleExecutiveService {
	Page<SaleExecutive> getSaleExecutives(int page, int size, String username, String email, String phoneNumber,
			Status status);

	SaleExecutive getSaleExecutive(Long id);

	SaleExecutive addSaleExecutive(String username, String email, String password, String phoneNumber);

	SaleExecutive updateSaleExecutive(Long id, String email, String phoneNumber);

	void deleteSaleExecutive(Long id);
}
