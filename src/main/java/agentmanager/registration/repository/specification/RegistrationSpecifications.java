package agentmanager.registration.repository.specification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import agentmanager.registration.model.Registration;

public class RegistrationSpecifications {

	public static Specification<Registration> withAgentName(String agentName) {
		if (agentName == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("agentName"), "%" + agentName + "%");
	}

	public static Specification<Registration> withPhoneNumber(String phoneNumber) {
		if (phoneNumber == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
	}

	public static Specification<Registration> laterThanRegisteredAt(Date registeredAt) {
		if (registeredAt == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("registeredAt"), registeredAt);
	}

	public static Specification<Registration> withSaleExecutive(Long saleExecutiveId) {
		if (saleExecutiveId == null)
			return null;
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("saleExecutive").get("id"),
				saleExecutiveId);
	}

}
