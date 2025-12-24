package agentmanager.test.saleexecutive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import agentmanager.saleexecutive.service.SaleExecutiveService;
import agentmanager.saleexecutive.service.SaleExecutiveServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SaleExecutiveServiceConstructorInjecitonTest {

	private SaleExecutiveService saleExecutiveService;

	@Mock
	private SaleExecutiveRepository saleExecutiveRepository;

	@Before
	public void setup() {
		saleExecutiveService = new SaleExecutiveServiceImpl(saleExecutiveRepository);
	}

	@Test
	public void testGetSaleExecutives() {
		List<SaleExecutive> saleExecutives = new ArrayList<>();
		saleExecutives.add(new SaleExecutive("executive1", "executive1@gmail.com", "pass1", "09123321123"));
		saleExecutives.add(new SaleExecutive("executive2", "executive2@gmail.com", "pass2", "09123321894"));
		Page<SaleExecutive> saleExecutivePage = convertListToPage(saleExecutives, 0, 10);

		when(saleExecutiveRepository.findAll(any(Specification.class), any(Pageable.class)))
				.thenReturn(saleExecutivePage);

		List<SaleExecutive> result = saleExecutiveService.getSaleExecutives(0, 10, null, null, null, null);
		assertEquals(2, result.size());

		verify(saleExecutiveRepository).findAll(any(Specification.class), any(Pageable.class));
	}

	@Test
	public void testGetSaleExecutive() {
		SaleExecutive saleExecutive = new SaleExecutive("executive1", "executive1@gmail.com", "pass1", "09123321123");

		when(saleExecutiveRepository.findById(anyLong())).thenReturn(Optional.of(saleExecutive));

		SaleExecutive result = saleExecutiveService.getSaleExecutive(anyLong());
		assertEquals("executive1", result.getUsername());
		assertEquals("executive1@gmail.com", result.getEmail());
		assertNotNull(result.getPassword());
		assertNotEquals("pass1", result.getPassword());
		assertEquals("09123321123", result.getPhoneNumber());

		verify(saleExecutiveRepository).findById(anyLong());
	}

	@Test
	public void testAddSaleExecutive() {
		SaleExecutive saleExecutive = new SaleExecutive("executive1", "executive1@gmail.com", "pass1", "09123321123");

		when(saleExecutiveRepository.save(any(SaleExecutive.class))).thenReturn(saleExecutive);

		SaleExecutive result = saleExecutiveService.addSaleExecutive("executive1", "executive1@gmail.com", "pass1",
				"09123321123");
		assertEquals("executive1", result.getUsername());
		assertEquals("executive1@gmail.com", result.getEmail());
		assertNotNull(result.getPassword());
		assertNotEquals("pass1", result.getPassword());
		assertEquals("09123321123", result.getPhoneNumber());

		verify(saleExecutiveRepository).save(any(SaleExecutive.class));
	}

	@Test
	public void testUpdateSaleExecutive() {
		SaleExecutive saleExecutive = new SaleExecutive("executive1", "executive1@gmail.com", "pass1", "09123321123");

		when(saleExecutiveRepository.findById(anyLong())).thenReturn(Optional.of(saleExecutive));
		when(saleExecutiveRepository.save(any(SaleExecutive.class))).thenReturn(saleExecutive);

		SaleExecutive result = saleExecutiveService.updateSaleExecutive(1L, "test@gmail.com", "0129");
		assertEquals("executive1", result.getUsername());
		assertEquals("test@gmail.com", result.getEmail());
		assertNotNull(result.getPassword());
		assertNotEquals("pass1", result.getPassword());
		assertEquals("0129", result.getPhoneNumber());

		verify(saleExecutiveRepository).save(any(SaleExecutive.class));
	}

	@Test
	public void testDeleteSaleExecutive() {
		saleExecutiveService.deleteSaleExecutive(anyLong());

		verify(saleExecutiveRepository).deleteById(anyLong());
	}

	private <T> Page<T> convertListToPage(List<T> list, int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);

		int start = (int) pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), list.size());

		List<T> pageContent = list.subList(start, end);
		return new PageImpl<>(pageContent, pageRequest, list.size());
	}

}
