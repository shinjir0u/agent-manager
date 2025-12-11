package agentmanager.registration.service;

import java.util.List;

import agentmanager.registration.model.Registration;

public interface RegistrationService {
	List<Registration> getRegistrations();

	Registration getRegistration(Long id);

	Registration addRegistration(Registration registration);

	Registration updaRegistration(Long id, Registration registration);

	void deleteRegistration(Long id);
}
