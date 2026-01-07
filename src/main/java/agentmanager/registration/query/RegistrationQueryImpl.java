package agentmanager.registration.query;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

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

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(RegistrationFilter.withAgentName(agentName)).and(RegistrationFilter.withPhoneNumber(phoneNumber))
				.and(RegistrationFilter.withRegisteredAt(registeredAt))
				.and(RegistrationFilter.withSaleExecutiveId(saleExecutiveId));

		return registrationRepository.findAll(builder,
				PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));
	}

}
