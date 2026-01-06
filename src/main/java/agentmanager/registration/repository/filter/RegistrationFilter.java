package agentmanager.registration.repository.filter;

import java.util.Date;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.registration.model.QRegistration;

public class RegistrationFilter {

	public static BooleanExpression withAgentName(String agentName) {
		return QRegistration.registration.agentName.like(agentName);
	}

	public static BooleanExpression withPhoneNumber(String phoneNumber) {
		return QRegistration.registration.agentName.eq(phoneNumber);
	}

	public static BooleanExpression withRegisteredAt(Date date) {
		return QRegistration.registration.registeredAt.eq(date);
	}

	public static BooleanExpression withSaleExecutiveId(Long saleExecutiveId) {
		return QRegistration.registration.saleExecutive.id.eq(saleExecutiveId);
	}

}
