package agentmanager.saleexecutive.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;

@Service
public class SaleExecutiveServiceImpl implements SaleExecutiveService {

	@Autowired
	private SaleExecutiveRepository saleExecutiveRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<SaleExecutive> getSaleExecutives() {
		List<SaleExecutive> saleExecutives = new ArrayList<>();
		saleExecutiveRepository.findAll().forEach(saleExecutives::add);
		return saleExecutives;
	}

	@Override
	public SaleExecutive getSaleExecutive(Long id) {
		Optional<SaleExecutive> saleExecutiveOptional = saleExecutiveRepository.findById(id);
		return saleExecutiveOptional.orElse(null);
	}

	@Override
	public SaleExecutive addSaleExecutive(SaleExecutive saleExecutive) {
		saleExecutive.encodePassword(passwordEncoder);
		SaleExecutive saleExecutiveAdded = saleExecutiveRepository.save(saleExecutive);
		return saleExecutiveAdded;
	}

	@Override
	public SaleExecutive updateSaleExecutive(Long id, SaleExecutive saleExecutive) {
		SaleExecutive saleExecutiveToAdd = saleExecutive.toBuilder().id(id).build();
		saleExecutive.encodePassword(passwordEncoder);
		SaleExecutive saleExecutiveUpdated = saleExecutiveRepository.save(saleExecutiveToAdd);
		return saleExecutiveUpdated;
	}

	@Override
	public void deleteSaleExecutive(Long id) {
		saleExecutiveRepository.deleteById(id);
	}

}
