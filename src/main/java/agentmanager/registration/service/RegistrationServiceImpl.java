package agentmanager.registration.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import agentmanager.registration.model.Registration;
import agentmanager.registration.query.RegistrationQuery;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.saleexecutive.model.SaleExecutive;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

	private final RegistrationRepository registrationRepository;

	private final RegistrationQuery registrationQuery;

	@Override
	public Page<Registration> getRegistrations(Integer page, Integer size, String sort, String direction,
			String agentName, String phoneNumber, Date registeredAt, Long saleExecutiveId) {
		Page<Registration> registrations = registrationQuery.getRegistrations(page, size, sort, direction, agentName,
				phoneNumber, registeredAt, saleExecutiveId);
		return registrations;
	}

	@Override
	public Page<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive, Integer page, Integer size,
			String sort, String direction, String agentName, String phoneNumber, Date registeredAt) {
		Page<Registration> registrations = registrationQuery.getRegistrations(page, size, sort, direction, agentName,
				phoneNumber, registeredAt, saleExecutive.getId());
		return registrations;
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
