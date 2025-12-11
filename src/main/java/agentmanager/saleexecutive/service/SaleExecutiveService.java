package agentmanager.saleexecutive.service;

import java.util.List;

import agentmanager.saleexecutive.model.SaleExecutive;

public interface SaleExecutiveService {
	List<SaleExecutive> getSaleExecutives();

	SaleExecutive getSaleExecutive(Long id);

	SaleExecutive addSaleExecutive(SaleExecutive saleExecutive);

	SaleExecutive updateSaleExecutive(Long id, SaleExecutive saleExecutive);

	void deleteSaleExecutive(Long id);
}
