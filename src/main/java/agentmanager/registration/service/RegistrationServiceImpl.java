package agentmanager.registration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Override
	public List<Registration> getRegistrations() {
		List<Registration> registrations = new ArrayList<>();
		registrationRepository.findAll().forEach(registrations::add);
		return registrations;
	}

	@Override
	public Registration getRegistration(Long id) {
		Optional<Registration> registrationOptional = registrationRepository.findById(id);
		if (!registrationOptional.isPresent())
			throw new IllegalArgumentException("No such registraction with id: " + id);
		return registrationOptional.get();
	}

	@Override
	public Registration addRegistration(Registration registration) {
		registrationRepository.save(registration);
		return registration;
	}

	@Override
	public Registration updaRegistration(Long id, Registration registration) {
		Registration registrationToAdd = registration.toBuilder().id(id).build();
		registrationRepository.save(registrationToAdd);
		return registrationToAdd;
	}

	@Override
	public void deleteRegistration(Long id) {
		registrationRepository.deleteById(id);
	}

}
