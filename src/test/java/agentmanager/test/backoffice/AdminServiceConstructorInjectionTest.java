package agentmanager.test.backoffice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.backoffice.service.AdminService;
import agentmanager.backoffice.service.AdminServiceImpl;
import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceConstructorInjectionTest {

	private AdminService adminService;

	@Mock
	private AdminRepository adminRepository;

	@Mock
	private SaleExecutiveRepository saleExecutiveRepository;

	@Mock
	private RegistrationRepository registrationRepository;

	@Before
	public void setup() {
		adminService = new AdminServiceImpl(adminRepository, saleExecutiveRepository, registrationRepository);
	}

	@Test
	public void testGetAdmins() {
		Admin[] mockAdmins = new Admin[] { new Admin(1L, "super_admin", "admin1@company.com", "pass123"),
				new Admin(2L, "hr_manager", "hr@company.com", "secure456"),
				new Admin(3L, "tech_lead", "tech@company.com", "dev789") };

		Page<Admin> adminPage = convertListToPage(Arrays.asList(mockAdmins), 0, 5);
		when(adminRepository.findAll(PageRequest.of(0, 5))).thenReturn(adminPage);

		List<Admin> admins = adminService.getAdmins(0, 5);
		assertEquals(3, admins.size());

		verify(adminRepository).findAll(PageRequest.of(0, 5));
	}

	@Test
	public void testGetAdmin() {
		Admin mockAdmin = new Admin(4L, "sale_lead", "sale@company.com", "sale012");

		when(adminRepository.findById(anyLong())).thenReturn(Optional.of(mockAdmin));

		Admin admin = adminService.getAdmin(4L);
		assertEquals(new Long(4), admin.getId());
		assertEquals("sale_lead", admin.getUsername());
		assertEquals("sale@company.com", admin.getEmail());
		assertEquals("sale012", admin.getPassword());

		verify(adminRepository).findById(anyLong());
	}

	@Test
	public void testAddAdmin() {
		Admin mockAdmin = new Admin(4L, "sale_lead", "sale@company.com", "sale012");

		when(adminRepository.save(any(Admin.class))).thenReturn(mockAdmin);

		Admin admin = adminService.addAdmin("sale_lead", "sale@company.com", "sale012");
		assertEquals(new Long(4L), admin.getId());
		assertEquals("sale_lead", admin.getUsername());
		assertEquals("sale@company.com", admin.getEmail());
		assertEquals("sale012", admin.getPassword());

		verify(adminRepository).save(any(Admin.class));
	}

	@Test
	public void testUpdateAdmin() {
		Admin mockAdmin = new Admin(4L, "sale_lead", "sale@company.com", "sale012");

		when(adminRepository.findById(anyLong())).thenReturn(Optional.of(mockAdmin));
		when(adminRepository.save(any(Admin.class))).thenReturn(mockAdmin);

		Admin admin = adminService.updateAdmin(4L, "test@company.com");
		assertEquals(new Long(4L), admin.getId());
		assertEquals("sale_lead", admin.getUsername());
		assertEquals("test@company.com", admin.getEmail());
		assertEquals("sale012", admin.getPassword());

		verify(adminRepository).findById(anyLong());
		verify(adminRepository).save(any(Admin.class));
	}

	@Test
	public void testDeleteAdmin() {
		adminService.deleteAdmin(1L);

		verify(adminRepository).deleteById(anyLong());
	}

	@Test
	public void testTerminateSaleExecutive() {
		SaleExecutive mockSaleExecutive = new SaleExecutive(1L, "saler", "saler@gmail.com", "saleislife", "09123456789",
				Status.ACTIVE, new ArrayList<>());

		when(saleExecutiveRepository.findById(anyLong())).thenReturn(Optional.of(mockSaleExecutive));
		when(saleExecutiveRepository.save(any(SaleExecutive.class))).thenReturn(mockSaleExecutive);

		SaleExecutive saleExecutive = adminService.terminateSaleExecutive(1L);
		assertNotNull(saleExecutive);
		assertEquals(Status.TERMINATED, saleExecutive.getStatus());

		verify(saleExecutiveRepository).findById(anyLong());
		verify(saleExecutiveRepository).save(any(SaleExecutive.class));
	}

	@Test
	public void testReassignRegistrationsToNewSaleExecutive() {
		Registration registration = new Registration(1L, "Agent1", "09222", new Date(), null);
		Registration registration2 = new Registration(2L, "Agent2", "09222", new Date(), null);

		SaleExecutive mockSaleExecutive = new SaleExecutive(1L, "saler", "saler@gmail.com", "saleislife", "09123456789",
				Status.ACTIVE, new ArrayList<>());
		SaleExecutive mockSaleExecutive2 = new SaleExecutive(2L, "saler2", "saler2@gmail.com", "saleislife2",
				"091234567892", Status.ACTIVE, new ArrayList<>());
		mockSaleExecutive.getRegistrations().add(registration);
		mockSaleExecutive2.getRegistrations().add(registration2);

		when(saleExecutiveRepository.findById(1L)).thenReturn(Optional.of(mockSaleExecutive));
		when(saleExecutiveRepository.findById(2L)).thenReturn(Optional.of(mockSaleExecutive2));

		when(saleExecutiveRepository.save(any(SaleExecutive.class))).thenReturn(mockSaleExecutive2);

		SaleExecutive saleExecutive = adminService.reassignRegistrationsToNewSaleExecutive(1L, 2L);

		verify(saleExecutiveRepository, times(2)).findById(anyLong());
		verify(saleExecutiveRepository).save(any(SaleExecutive.class));

		assertEquals(2, saleExecutive.getRegistrations().size());
	}

	private <T> Page<T> convertListToPage(List<T> list, int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);

		int start = (int) pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), list.size());

		List<T> pageContent = list.subList(start, end);
		return new PageImpl<>(pageContent, pageRequest, list.size());
	}

}
