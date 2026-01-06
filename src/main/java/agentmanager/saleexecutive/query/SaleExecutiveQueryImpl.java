package agentmanager.saleexecutive.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import agentmanager.saleexecutive.repository.filter.SaleExecutiveFilter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SaleExecutiveQueryImpl implements SaleExecutiveQuery {

	private final SaleExecutiveRepository saleExecutiveRepository;

	@Override
	public Page<SaleExecutive> getSaleExecutives(Integer page, Integer size, String sort, String direction,
			String username, String email, String phoneNumber, Status status) {

		BooleanExpression where = null;
		if (username != null)
			where = SaleExecutiveFilter.withUsername(username);

		if (email != null)
			where = (where != null) ? where.and(SaleExecutiveFilter.withEmail(email))
					: SaleExecutiveFilter.withEmail(email);

		if (phoneNumber != null)
			where = (where != null) ? where.and(SaleExecutiveFilter.withPhoneNumber(phoneNumber))
					: SaleExecutiveFilter.withPhoneNumber(phoneNumber);

		if (status != null)
			where = (where != null) ? where.and(SaleExecutiveFilter.withStatus(status))
					: SaleExecutiveFilter.withStatus(status);

		Page<SaleExecutive> saleExecutives = null;
		if (where != null)
			saleExecutives = saleExecutiveRepository.findAll(where,
					PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));
		else
			saleExecutives = saleExecutiveRepository
					.findAll(PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));

		return saleExecutives;
	}

}
