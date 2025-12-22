package agentmanager.registration.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public List<Registration> getRegistrations(int page, int size) {
		Page<Registration> registrations = registrationRepository.findAll(PageRequest.of(page, size));
		return registrations.getContent();
	}

	@Override
	public List<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive, int page, int perPage) {
		Page<Registration> registrations = registrationRepository.findBySaleExecutive(saleExecutive,
				PageRequest.of(page, perPage));
		return registrations.getContent();
	}

	@Override
	public Registration getRegistration(Long id) {
		Optional<Registration> registrationOptional = registrationRepository.findById(id);
		return registrationOptional.orElse(null);
	}

	@Override
	public Registration addRegistration(String agentName, String phoneNumber, SaleExecutive saleExecutive) {
		Registration registrationToAdd = new Registration(agentName, phoneNumber, saleExecutive);
		Registration registrationAdded = registrationRepository.save(registrationToAdd);
		return registrationAdded;
	}

	@Override
	public Registration updateRegistration(Long id, String phoneNumber) {
		Registration registrationFetched = getRegistration(id);
		registrationFetched.update(phoneNumber);
		Registration registrationUpdated = registrationRepository.save(registrationFetched);
		return registrationUpdated;
	}

	@Override
	public void deleteRegistration(Long id) {
		registrationRepository.deleteById(id);
	}

}
