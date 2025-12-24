package agentmanager.saleexecutive.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import agentmanager.saleexecutive.model.SaleExecutive;
import agentmanager.saleexecutive.model.Status;

public class SaleExecutiveSpecifications {

	public static Specification<SaleExecutive> withUsername(String username) {
		if (username == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + username + "%");
	}

	public static Specification<SaleExecutive> withEmail(String email) {
		if (email == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
	}

	public static Specification<SaleExecutive> withPhoneNumber(String phoneNumber) {
		if (phoneNumber == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
	}

	public static Specification<SaleExecutive> withStatus(Status status) {
		if (status == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
	}
}
