package agentmanager.test.saleexecutive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import agentmanager.config.PersistenceConfig;
import agentmanager.registration.model.Registration;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import agentmanager.saleexecutive.service.SaleExecutiveService;
import agentmanager.saleexecutive.service.SaleExecutiveServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, SaleExecutiveServiceImpl.class,
		SaleExecutiveRepository.class })
public class SaleExecutiveServiceTest {

	@Autowired
	private SaleExecutiveService saleExecutiveService;

	@Test
	public void testGetSaleExecutives() {
		List<SaleExecutive> saleExecutives = saleExecutiveService.getSaleExecutives();

		assertNotNull(saleExecutives);
		assertThat(saleExecutives.size() > 0);
	}

	@Test
	public void testGetSaleExecutive() {
		SaleExecutive saleExecutive = saleExecutiveService.getSaleExecutive(1L);

		assertNotNull(saleExecutive);
		assertEquals(saleExecutive.getUsername(), "dse001");
		assertEquals(saleExecutive.getEmail(), "dse001@example.com");
		assertNotNull(saleExecutive.getPassword());
		assertEquals(saleExecutive.getPhoneNumber(), "09410000001");
	}

	@Test
	public void testCreateSaleExecutive() {
		SaleExecutive saleExecutive = SaleExecutive.builder().username("test").email("test@gmail.com").password("123")
				.phoneNumber("09123456789").build();
		SaleExecutive saleExecutiveAdded = saleExecutiveService.addSaleExecutive(saleExecutive);

		assertNotNull(saleExecutiveAdded);
		assertNotNull(saleExecutiveAdded.getId());
		assertEquals(saleExecutiveAdded.getUsername(), "test");
		assertEquals(saleExecutiveAdded.getEmail(), "test@gmail.com");
		assertEquals(saleExecutiveAdded.getPhoneNumber(), "09123456789");
		assertNotNull(saleExecutiveAdded.getPassword());
	}

	@Test
	public void testUpdateSaleExecutive() {
		SaleExecutive saleExecutive = SaleExecutive.builder().username("test1").email("test1@gmail.com").password("123")
				.phoneNumber("09987654321").build();
		SaleExecutive saleExecutiveUpdated = saleExecutiveService.updateSaleExecutive(5L, saleExecutive);

		assertNotNull(saleExecutiveUpdated);
		assertNotNull(saleExecutiveUpdated.getId());
		assertEquals(saleExecutiveUpdated.getUsername(), "test1");
		assertEquals(saleExecutiveUpdated.getEmail(), "test1@gmail.com");
		assertEquals(saleExecutiveUpdated.getPhoneNumber(), "09987654321");
		assertNotNull(saleExecutiveUpdated.getPassword());
	}

	@Test
	public void testDeleteSaleExecutive() {
		saleExecutiveService.deleteSaleExecutive(4L);

		assertNull(saleExecutiveService.getSaleExecutive(4L));
	}

	@Test
	public void testAddAgent() {
		Date now = new Date();
		Registration registration = Registration.builder().agentName("Agent I").phoneNumber("09876567898")
				.registeredAt(now).build();

		Registration registrationAdded = saleExecutiveService.addAgent(2L, registration);

		assertNotNull(registrationAdded);
		assertNotNull(registrationAdded.getId());
		assertEquals(registrationAdded.getAgentName(), "Agent I");
		assertEquals(registrationAdded.getPhoneNumber(), "09876567898");
		assertEquals(registrationAdded.getRegisteredAt(), now);
	}

}
