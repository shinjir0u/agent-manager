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

	private final SaleExecutiveService saleExecutiveService;

	@Autowired
	public RegistrationServiceImpl(RegistrationRepository registrationRepository,
			SaleExecutiveService saleExecutiveService) {
		this.registrationRepository = registrationRepository;
		this.saleExecutiveService = saleExecutiveService;
	}

	@Override
	public List<Registration> getRegistrations(Long saleExecutiveId) {
		List<Registration> registrations = new ArrayList<>();
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);

		registrationRepository.findBySaleExecutive(saleExecutive).forEach(registrations::add);
		return registrations;
	}

	@Override
	public Registration getRegistration(Long saleExecutiveId, Long registrationId) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);

		Optional<Registration> registrationOptional = registrationRepository.findBySaleExecutiveAndId(saleExecutive,
				registrationId);
		return registrationOptional.orElse(null);
	}

	@Override
	public Registration addRegistration(Long saleExecutiveId, Registration registration) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);
		registration.setSaleExecutive(saleExecutive);

		Registration registrationAdded = registrationRepository.save(registration);
		return registrationAdded;
	}

	@Override
	public Registration updateRegistration(Long saleExecutiveId, Long registrationId, Registration registration) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);
		Registration registrationToAdd = registration.toBuilder().id(registrationId).saleExecutive(saleExecutive)
				.build();
		Registration registrationUpdated = registrationRepository.save(registrationToAdd);
		return registrationUpdated;
	}

	@Override
	public void deleteRegistration(Long saleExecutiveId, Long registrationId) {
		SaleExecutive saleExecutive = getSaleExecutive(saleExecutiveId);

		registrationRepository.deleteBySaleExecutiveAndId(saleExecutive, registrationId);
	}

	private SaleExecutive getSaleExecutive(Long id) {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(id);
		if (saleExecutive == null)
			throw new IllegalArgumentException("No such sale executive with id: " + id);
		return saleExecutive;
	}

}
