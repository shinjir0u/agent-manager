package agentmanager.registration.service;

import java.util.List;

import agentmanager.registration.model.Registration;

public interface RegistrationService {
	List<Registration> getRegistrations(Long saleExecutiveId);

	Registration getRegistration(Long saleExecutiveId, Long registrationId);

	Registration addRegistration(Long saleExecutiveId, Registration registration);

	Registration updateRegistration(Long saleExecutiveId, Long registrationId, Registration registration);

	void deleteRegistration(Long saleExecutiveId, Long registrationId);
}
