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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, AdminServiceImpl.class, AdminRepository.class })
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;

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

//	@Test
//	public void testCreateAdmin() {
//		Admin adminToAdd = Admin.builder().username("test").email("test@gmail.com").password("123").build();
//		Admin adminAdded = adminService.addAdmin(adminToAdd);
//
//		assertNotNull(adminAdded);
//		assertEquals(adminAdded.getUsername(), "test");
//		assertEquals(adminAdded.getEmail(), "test@gmail.com");
//		assertNotNull(adminAdded.getPassword());
//	}
//
//	@Test
//	public void testUpdateAdmin() {
//		Admin adminToAdd = Admin.builder().username("test1").email("test1@gmail.com").password("123").build();
//		Admin adminUpdated = adminService.updateAdmin(13L, adminToAdd);
//
//		assertNotNull(adminUpdated);
//		assertEquals(adminUpdated.getId(), new Long(13L));
//		assertEquals(adminUpdated.getUsername(), "test1");
//		assertEquals(adminUpdated.getEmail(), "test1@gmail.com");
//		assertNotNull(adminUpdated.getPassword());
//	}

	@Test
	public void testDeleteAdmin() {
		adminService.deleteAdmin(28L);

		assertNull(adminService.getAdmin(28L));
	}

}
