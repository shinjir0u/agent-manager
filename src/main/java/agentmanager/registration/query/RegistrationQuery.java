package agentmanager.registration.query;

import java.util.Date;

import org.springframework.data.domain.Page;

import agentmanager.registration.model.Registration;

public interface RegistrationQuery {

	Page<Registration> getRegistrations(Integer page, Integer size, String sort, String direction, String agentName,
			String phoneNumber, Date registeredAt, Long saleExecutiveId);

}
