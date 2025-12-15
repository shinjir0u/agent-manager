package agentmanager.test.registration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import agentmanager.config.PersistenceConfig;
import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.registration.service.RegistrationService;
import agentmanager.registration.service.RegistrationServiceImpl;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import agentmanager.saleexecutive.service.SaleExecutiveServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, RegistrationServiceImpl.class, SaleExecutiveRepository.class,
		SaleExecutiveServiceImpl.class, RegistrationRepository.class })

public class RegistrationServiceTest {

	@Autowired
	private SaleExecutiveRepository saleExecutiveRepository;

	@Autowired
	private RegistrationService registrationService;

	@Test
	public void testGetRegistrations() {
		List<Registration> registrations = registrationService.getRegistrations();

		assertNotNull(registrations);
		assertThat(registrations.size() > 0);
	}

	@Test
	public void testGetRegistrationsBySaleExecutive() {
		SaleExecutive saleExecutive = saleExecutiveRepository.findById(10L).orElse(null);
		List<Registration> registrations = registrationService.getRegistrationsBySaleExecutive(saleExecutive);

		assertNotNull(registrations);
		assertThat(registrations.size() > 0);
	}

	@Test
	public void testGetRegistration() {
		Registration registration = registrationService.getRegistration(1L);

		assertNotNull(registration);
		assertEquals(registration.getAgentName(), "Agent A");
		assertEquals(registration.getPhoneNumber(), "091234567");
		assertEquals(registration.getRegisteredAt(), Timestamp.valueOf("2025-12-10 16:43:40.154114"));
	}

	@Test
	public void testCreateRegistration() {
		SaleExecutive saleExecutive = saleExecutiveRepository.findById(10L).orElse(null);
		Date now = new Date();
		Registration registration = Registration.builder().agentName("Agent Pi").phoneNumber("09876567898")
				.registeredAt(now).build();
		Registration registrationAdded = registrationService.addRegistration(saleExecutive, registration);

		assertNotNull(registrationAdded);
		assertNotNull(registrationAdded.getId());
		assertEquals(registrationAdded.getAgentName(), "Agent Pi");
		assertEquals(registrationAdded.getPhoneNumber(), "09876567898");
		assertEquals(registrationAdded.getRegisteredAt(), now);
		assertNotNull(registrationAdded.getSaleExecutive());
	}

	@Test
	public void testUpdateRegistration() {
		Date now = new Date();
		Registration registration = Registration.builder().agentName("Agent PK").phoneNumber("09876567899")
				.registeredAt(now).build();
		Registration registrationUpdated = registrationService.updateRegistration(12L, registration);

		assertNotNull(registrationUpdated);
		assertNotNull(registrationUpdated.getId());
		assertEquals(registrationUpdated.getAgentName(), "Agent PK");
		assertEquals(registrationUpdated.getPhoneNumber(), "09876567899");
		assertEquals(registrationUpdated.getRegisteredAt(), now);
	}

	@Test
	public void testDeleteRegistration() {
		registrationService.deleteRegistration(11L);

		assertNull(registrationService.getRegistration(11L));
	}

}
