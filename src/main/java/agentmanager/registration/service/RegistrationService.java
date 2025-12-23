package agentmanager.registration.service;

import java.util.Date;
import java.util.List;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface RegistrationService {
	List<Registration> getRegistrations(int page, int size, String agentName, String phoneNumber, Date registeredAt,
			Long saleExecutiveId);

	List<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive, int page, int perPage,
			String agentName, String phoneNumber, Date registeredAt, Long saleExecutiveId);

	Registration getRegistration(Long registrationId);

	Registration addRegistration(String agentName, String phoneNumber, SaleExecutive saleExecutive);

	Registration updateRegistration(Long registrationId, String phoneNumber);

	void deleteRegistration(Long registrationId);
}
