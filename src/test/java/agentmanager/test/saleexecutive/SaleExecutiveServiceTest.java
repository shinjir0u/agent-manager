package agentmanager.test.saleexecutive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import agentmanager.config.PersistenceConfig;
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
		List<SaleExecutive> saleExecutives = saleExecutiveService.getSaleExecutives(0, 10);

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
		SaleExecutive saleExecutiveAdded = saleExecutiveService.addSaleExecutive("testuser", "testuser@gmail.com",
				"123", "09123456789");

		assertNotNull(saleExecutiveAdded);
		assertNotNull(saleExecutiveAdded.getId());
		assertEquals(saleExecutiveAdded.getUsername(), "testuser");
		assertEquals(saleExecutiveAdded.getEmail(), "testuser@gmail.com");
		assertEquals(saleExecutiveAdded.getPhoneNumber(), "09123456789");
		assertNotNull(saleExecutiveAdded.getPassword());
	}

	@Test
	public void testUpdateSaleExecutive() {
		SaleExecutive saleExecutiveUpdated = saleExecutiveService.updateSaleExecutive(6L, "testmail@gmail.com",
				"09987654333");

		assertNotNull(saleExecutiveUpdated);
		assertNotNull(saleExecutiveUpdated.getId());
		assertEquals(saleExecutiveUpdated.getEmail(), "testmail@gmail.com");
		assertEquals(saleExecutiveUpdated.getPhoneNumber(), "09987654333");
	}

	@Test
	public void testDeleteSaleExecutive() {
		saleExecutiveService.deleteSaleExecutive(5L);

		assertNull(saleExecutiveService.getSaleExecutive(5L));
	}

}
