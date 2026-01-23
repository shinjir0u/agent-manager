package agentmanager.saleexecutive.service;

import org.springframework.data.domain.Page;

import agentmanager.common.model.token.Token;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.SaleExecutiveStatus;

public interface SaleExecutiveService {
	Token login(String username, String password);

	Page<SaleExecutive> getSaleExecutives(Integer page, Integer size, String sort, String direction, String username,
			String email, String phoneNumber, SaleExecutiveStatus status);

	SaleExecutive getSaleExecutive(Long id);

	SaleExecutive addSaleExecutive(String username, String email, String password, String phoneNumber);

	SaleExecutive updateSaleExecutive(Long id, String email, String phoneNumber);

	void deleteSaleExecutive(Long id);
}
