package agentmanager.backoffice.model.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

import agentmanager.backoffice.model.Admin;
import agentmanager.backoffice.repository.AdminRepository;
import agentmanager.backoffice.repository.filter.AdminFilter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AdminQueryImpl implements AdminQuery {

	private final AdminRepository adminRepository;

	@Override
	public Page<Admin> getAdmins(Integer page, Integer size, String sort, String direction, String username,
			String email) {

		BooleanBuilder builder = new BooleanBuilder();
		builder.and(AdminFilter.withUsername(username)).and(AdminFilter.withEmail(email));

		return adminRepository.findAll(builder, PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));

	}

}
