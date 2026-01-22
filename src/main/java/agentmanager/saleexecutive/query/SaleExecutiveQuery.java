package agentmanager.saleexecutive.query;

import org.springframework.data.domain.Page;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.SaleExecutiveStatus;

public interface SaleExecutiveQuery {

	Page<SaleExecutive> getSaleExecutives(Integer page, Integer size, String sort, String direction, String username,
			String email, String phoneNumber, SaleExecutiveStatus status);

}
