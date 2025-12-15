package agentmanager.registration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.service.SaleExecutiveService;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

	private final RegistrationRepository registrationRepository;

	@Autowired
	public RegistrationServiceImpl(RegistrationRepository registrationRepository,
			SaleExecutiveService saleExecutiveService) {
		this.registrationRepository = registrationRepository;
	}

	@Override
	public List<Registration> getRegistrations() {
		List<Registration> registrations = new ArrayList<>();
		registrationRepository.findAll().forEach(registrations::add);
		return registrations;
	}

	@Override
	public List<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive) {
		List<Registration> registrations = new ArrayList<>();

		registrationRepository.findBySaleExecutive(saleExecutive).forEach(registrations::add);
		return registrations;
	}

	@Override
	public Registration getRegistration(Long id) {
		Optional<Registration> registrationOptional = registrationRepository.findById(id);
		return registrationOptional.orElse(null);
	}

	@Override
	public Registration addRegistration(SaleExecutive saleExecutive, Registration registration) {
		Registration registrationToAdd = registration.toBuilder().saleExecutive(saleExecutive).build();
		Registration registrationAdded = registrationRepository.save(registrationToAdd);
		return registrationAdded;
	}

	@Override
	public Registration updateRegistration(Long id, Registration registration) {
		Registration registrationToAdd = registration.toBuilder().id(id).build();
		Registration registrationUpdated = registrationRepository.save(registrationToAdd);
		return registrationUpdated;
	}

	@Override
	public void deleteRegistration(Long id) {
		registrationRepository.deleteById(id);
	}

}
