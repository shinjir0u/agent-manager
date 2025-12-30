package agentmanager.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.repository.SaleExecutiveRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserInfoDetailsService implements UserDetailsService {

	private final AdminRepository adminRepository;

	private final SaleExecutiveRepository saleExecutiveRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Admin> adminOptional = adminRepository.findByUsername(username);
		if (adminOptional.isPresent()) {
			Admin admin = adminOptional.get();
			List<String> roles = new ArrayList<>();
			roles.add("USER");
			roles.add("ADMIN");

			return new UserInfoDetails(admin.getUsername(), admin.getPassword(), roles);
		}

		Optional<SaleExecutive> saleExecutiveOptional = saleExecutiveRepository.findByUsername(username);
		if (saleExecutiveOptional.isPresent()) {
			SaleExecutive saleExecutive = saleExecutiveOptional.get();
			List<String> roles = new ArrayList<>();
			roles.add("USER");

			return new UserInfoDetails(saleExecutive.getUsername(), saleExecutive.getPassword(), roles);
		}

		throw new UsernameNotFoundException("No such user with username: " + username);

	}

}
