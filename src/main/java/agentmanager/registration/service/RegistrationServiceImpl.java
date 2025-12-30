package agentmanager.registration.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.registration.repository.specification.RegistrationSpecifications;
import agentmanager.saleexecutive.model.SaleExecutive;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

	private final RegistrationRepository registrationRepository;

	@Override
	public Page<Registration> getRegistrations(int page, int size, String agentName, String phoneNumber,
			Date registeredAt, Long saleExecutiveId) {
		Specification<Registration> specification = Specification
				.where(RegistrationSpecifications.withAgentName(agentName))
				.and(RegistrationSpecifications.withPhoneNumber(phoneNumber))
				.and(RegistrationSpecifications.laterThanRegisteredAt(registeredAt))
				.and(RegistrationSpecifications.withSaleExecutive(saleExecutiveId));
		Page<Registration> registrations = registrationRepository.findAll(specification, PageRequest.of(page, size));
		return registrations;
	}

	@Override
	public Page<Registration> getRegistrationsBySaleExecutive(SaleExecutive saleExecutive, int page, int perPage,
			String agentName, String phoneNumber, Date registeredAt) {
		Specification<Registration> specification = Specification
				.where(RegistrationSpecifications.withAgentName(agentName))
				.and(RegistrationSpecifications.withPhoneNumber(phoneNumber))
				.and(RegistrationSpecifications.laterThanRegisteredAt(registeredAt))
				.and(RegistrationSpecifications.withSaleExecutive(saleExecutive.getId()));
		Page<Registration> registrations = registrationRepository.findAll(specification, PageRequest.of(page, perPage));
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
