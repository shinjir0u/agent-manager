package agentmanager.registration.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface RegistrationService {
	Page<Registration> getRegistrations(int page, int size, String agentName, String phoneNumber, Date registeredAt,
			Long saleExecutiveId);

	Page<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive, int page, int perPage,
			String agentName, String phoneNumber, Date registeredAt);

	Registration getRegistration(Long registrationId);

	Registration addRegistration(String agentName, String phoneNumber, SaleExecutive saleExecutive);

	Registration updateRegistration(Long registrationId, String phoneNumber);

	void deleteRegistration(Long registrationId);
}
