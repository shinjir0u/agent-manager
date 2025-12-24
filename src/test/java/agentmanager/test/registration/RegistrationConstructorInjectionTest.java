package agentmanager.test.registration;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.data.jpa.domain.Specification;

import agentmanager.registration.model.Registration;
import agentmanager.registration.repository.RegistrationRepository;
import agentmanager.registration.service.RegistrationService;
import agentmanager.registration.service.RegistrationServiceImpl;
import agentmanager.saleexecutive.model.SaleExecutive;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationConstructorInjectionTest {

	private RegistrationService registrationService;

	@Mock
	private RegistrationRepository registrationRepository;

	@Before
	public void setup() {
		registrationService = new RegistrationServiceImpl(registrationRepository);
	}

	@Test
	public void testGetRegistrations() {
		List<Registration> registrations = new ArrayList<>();
		registrations.add(new Registration("agent1", "09123", new SaleExecutive()));
		registrations.add(new Registration("agent2", "09456", new SaleExecutive()));

		Page<Registration> registrationPage = convertListToPage(registrations, 0, 10);
		when(registrationRepository.findAll(any(Specification.class), any(Pageable.class)))
				.thenReturn(registrationPage);

		List<Registration> result = registrationService.getRegistrations(0, 10, null, null, null, null);
		assertEquals(2, result.size());

		verify(registrationRepository).findAll(any(Specification.class), any(Pageable.class));
	}

	@Test
	public void testGetRegistrationsBySaleExecutive() {
		List<Registration> registrations = new ArrayList<>();
		SaleExecutive saleExecutive = new SaleExecutive();
		registrations.add(new Registration("agent1", "09123", saleExecutive));
		registrations.add(new Registration("agent2", "09456", saleExecutive));

		Page<Registration> registrationPage = convertListToPage(registrations, 0, 10);
		when(registrationRepository.findAll(any(Specification.class), any(Pageable.class)))
				.thenReturn(registrationPage);

		List<Registration> result = registrationService.getRegistrationsBySaleExecutive(saleExecutive, 0, 10, null,
				null, null);
		assertEquals(2, result.size());

		verify(registrationRepository).findAll(any(Specification.class), any(Pageable.class));
	}

	@Test
	public void testGetRegistration() {
		Registration registration = new Registration("agent1", "09123", new SaleExecutive());

		when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));

		Registration result = registrationService.getRegistration(1L);
		assertEquals("agent1", result.getAgentName());
		assertEquals("09123", result.getPhoneNumber());

		verify(registrationRepository).findById(anyLong());
	}

	@Test
	public void testAddRegistration() {
		Registration registration = new Registration("agent1", "09123", new SaleExecutive());

		when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

		Registration result = registrationService.addRegistration("agent1", "09123", new SaleExecutive());
		assertEquals("agent1", result.getAgentName());
		assertEquals("09123", result.getPhoneNumber());

		verify(registrationRepository).save(any(Registration.class));
	}

	@Test
	public void testUpdateRegistration() {
		Registration registration = new Registration("agent1", "09123", new SaleExecutive());

		when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
		when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

		Registration result = registrationService.updateRegistration(anyLong(), "09456");
		assertEquals("agent1", result.getAgentName());
		assertEquals("09456", result.getPhoneNumber());

		verify(registrationRepository).save(any(Registration.class));
	}

	@Test
	public void testDeleteRegistration() {
		registrationService.deleteRegistration(anyLong());

		verify(registrationRepository).deleteById(anyLong());
	}

	private <T> Page<T> convertListToPage(List<T> list, int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);

		int start = (int) pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), list.size());

		List<T> pageContent = list.subList(start, end);
		return new PageImpl<>(pageContent, pageRequest, list.size());
	}

}
