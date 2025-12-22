package agentmanager.saleexecutive.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SaleExecutiveServiceImpl implements SaleExecutiveService {

	private final SaleExecutiveRepository saleExecutiveRepository;

	@Override
	public List<SaleExecutive> getSaleExecutives(int page, int size) {
		Page<SaleExecutive> saleExecutives = saleExecutiveRepository.findAll(PageRequest.of(page, size));
		return saleExecutives.getContent();
	}

	@Override
	public SaleExecutive getSaleExecutive(Long id) {
		Optional<SaleExecutive> saleExecutiveOptional = saleExecutiveRepository.findById(id);
		return saleExecutiveOptional.orElse(null);
	}

	@Override
	public SaleExecutive addSaleExecutive(String username, String email, String password, String phoneNumber) {
		SaleExecutive saleExecutiveToAdd = new SaleExecutive(username, email, password, phoneNumber);
		SaleExecutive saleExecutiveAdded = saleExecutiveRepository.save(saleExecutiveToAdd);
		return saleExecutiveAdded;
	}

	@Override
	public SaleExecutive updateSaleExecutive(Long id, String email, String phoneNumber) {
		SaleExecutive saleExecutiveFetched = getSaleExecutive(id);
		saleExecutiveFetched.update(email, phoneNumber);
		SaleExecutive saleExecutiveUpdated = saleExecutiveRepository.save(saleExecutiveFetched);
		return saleExecutiveUpdated;
	}

	@Override
	public void deleteSaleExecutive(Long id) {
		saleExecutiveRepository.deleteById(id);
	}

}
