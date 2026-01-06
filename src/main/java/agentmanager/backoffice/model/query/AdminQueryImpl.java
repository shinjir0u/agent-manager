package agentmanager.backoffice.model.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;

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

		BooleanExpression where = null;
		if (username != null)
			where = AdminFilter.withUsername(username);

		if (email != null)
			where = (where != null) ? where.and(AdminFilter.withEmail(email)) : AdminFilter.withEmail(email);

		Page<Admin> admins = null;
		if (where != null)
			admins = adminRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));
		else
			admins = adminRepository.findAll(where,
					PageRequest.of(page, size, Sort.Direction.fromString(direction), sort));

		return admins;
	}

}
