package agentmanager.saleexecutive.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

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

		BooleanBuilder builder = new BooleanBuilder();

		builder.and(SaleExecutiveFilter.withUsername(username)).and(SaleExecutiveFilter.withEmail(email))
				.and(SaleExecutiveFilter.withPhoneNumber(phoneNumber)).and(SaleExecutiveFilter.withStatus(status));

		return saleExecutiveRepository.findAll(builder,
				PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));
	}

}
