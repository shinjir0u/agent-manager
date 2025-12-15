package agentmanager.registration.service;

import java.util.List;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface RegistrationService {
	List<Registration> getRegistrations();

	List<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive);

	Registration getRegistration(Long registrationId);

	Registration addRegistration(SaleExecutive saleExecutive, Registration registration);

	Registration updateRegistration(Long registrationId, Registration registration);

	void deleteRegistration(Long registrationId);
}
