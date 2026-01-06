package agentmanager.registration.query;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.registration.repository.filter.RegistrationFilter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RegistrationQueryImpl implements RegistrationQuery {

	private final RegistrationRepository registrationRepository;

	@Override
	public Page<Registration> getRegistrations(Integer page, Integer size, String sort, String direction,
			String agentName, String phoneNumber, Date registeredAt, Long saleExecutiveId) {

		BooleanExpression where = null;
		if (agentName != null)
			where = RegistrationFilter.withAgentName(agentName);

		if (phoneNumber != null) {
			BooleanExpression phoneNumberExpression = RegistrationFilter.withPhoneNumber(phoneNumber);
			where = (where != null) ? where.and(phoneNumberExpression) : phoneNumberExpression;
		}

		if (registeredAt != null) {
			BooleanExpression registeredAtExpression = RegistrationFilter.withRegisteredAt(registeredAt);
			where = (where != null) ? where.and(registeredAtExpression) : registeredAtExpression;
		}

		if (saleExecutiveId != null) {
			BooleanExpression saleExecutiveIdExpression = RegistrationFilter.withSaleExecutiveId(saleExecutiveId);
			where = (where != null) ? where.and(saleExecutiveIdExpression) : saleExecutiveIdExpression;
		}

		if (where == null)
			return registrationRepository
					.findAll(PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));

		return registrationRepository.findAll(where,
				PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));
	}

}
