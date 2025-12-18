package agentmanager.test.backoffice;

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

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.backoffice.service.AdminService;
import agentmanager.backoffice.service.AdminServiceImpl;
import agentmanager.config.PersistenceConfig;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import agentmanager.saleexecutive.service.SaleExecutiveService;
import agentmanager.saleexecutive.service.SaleExecutiveServiceImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, AdminServiceImpl.class, AdminRepository.class,
		SaleExecutiveRepository.class, SaleExecutiveServiceImpl.class })
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;

	@Autowired
	private SaleExecutiveService saleExecutiveService;

	@Test
	public void testGetAdmins() {
		List<Admin> admins = adminService.getAdmins();

		assertNotNull(admins);
		assertThat(admins.size() > 0);
	}

	@Test
	public void testGetAdmin() {
		Admin admin = adminService.getAdmin(2L);

		assertNotNull(admin);
		assertEquals(admin.getUsername(), "admin2");
		assertEquals(admin.getEmail(), "admin2@example.com");
	}

	@Test
	public void testCreateAdmin() {
		Admin adminAdded = adminService.addAdmin("testing", "testing@gmail.com", "123");

		assertNotNull(adminAdded);
		assertEquals(adminAdded.getUsername(), "testing");
		assertEquals(adminAdded.getEmail(), "testing@gmail.com");
		assertNotNull(adminAdded.getPassword());
	}

	@Test
	public void testUpdateAdmin() {
		Admin adminUpdated = adminService.updateAdmin(30L, "testokay@gmail.com");

		assertNotNull(adminUpdated);
		assertEquals(adminUpdated.getId(), new Long(30L));
		assertEquals(adminUpdated.getEmail(), "testokay@gmail.com");
	}

	@Test
	public void testDeleteAdmin() {
		adminService.deleteAdmin(30L);

		assertNull(adminService.getAdmin(30L));
	}

	@Test
	public void testTerminateSaleExecutive() {
		SaleExecutive saleExecutive = adminService.terminateSaleExecutive(10L);

		assertEquals(saleExecutive.getStatus(), Status.TERMINATED);
	}

	@Test
	public void testReassignRegistrationsToNewSaleExecutive() {
		SaleExecutive saleExecutive = adminService.reassignRegistrationsToNewSaleExecutive(10L, 17L);
		SaleExecutive saleExecutiveTransferred = saleExecutiveService.getSaleExecutive(10L);

		assertEquals(saleExecutiveTransferred.getRegistrations().size(), 0);
		assertThat(saleExecutive.getRegistrations().size() > 1);
	}

}
