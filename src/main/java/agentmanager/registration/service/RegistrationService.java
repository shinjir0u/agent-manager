package agentmanager.registration.service;

import java.util.List;

import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;

public interface RegistrationService {
	List<Registration> getRegistrations();

	List<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive);

	Registration getRegistration(Long registrationId);

	Registration addRegistration(String agentName, String phoneNumber, SaleExecutive saleExecutive);

	Registration updateRegistration(Long registrationId, String phoneNumber);

	void deleteRegistration(Long registrationId);
}
