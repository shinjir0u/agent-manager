package agentmanager.backoffice.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import agentmanager.backoffice.model.Admin;

public class AdminSpecifications {

	public static Specification<Admin> withUsername(String username) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + username + "%");
	}

	public static Specification<Admin> withEmail(String email) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
	}

}
