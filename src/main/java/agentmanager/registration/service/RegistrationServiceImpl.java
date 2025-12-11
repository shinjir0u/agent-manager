package agentmanager.registration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;

@Service
@Transactional
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
		return registrationOptional.orElse(null);
	}

	@Override
	public Registration addRegistration(Registration registration) {
		Registration registrationAdded = registrationRepository.save(registration);
		return registrationAdded;
	}

	@Override
	public Registration updaRegistration(Long id, Registration registration) {
		Registration registrationToAdd = registration.toBuilder().id(id).build();
		Registration registrationUpdated = registrationRepository.save(registrationToAdd);
		return registrationUpdated;
	}

	@Override
	public void deleteRegistration(Long id) {
		registrationRepository.deleteById(id);
	}

}
