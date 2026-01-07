package agentmanager.registration.repository.filter;

import java.util.Date;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.registration.model.QRegistration;

public class RegistrationFilter {

	public static BooleanExpression withAgentName(String agentName) {
		if (agentName == null || agentName.isEmpty())
			return null;
		return QRegistration.registration.agentName.containsIgnoreCase(agentName);
	}

	public static BooleanExpression withPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty())
			return null;
		return QRegistration.registration.agentName.eq(phoneNumber);
	}

	public static BooleanExpression withRegisteredAt(Date date) {
		if (date == null)
			return null;
		return QRegistration.registration.registeredAt.eq(date);
	}

	public static BooleanExpression withSaleExecutiveId(Long saleExecutiveId) {
		if (saleExecutiveId == null)
			return null;
		return QRegistration.registration.saleExecutive.id.eq(saleExecutiveId);
	}

}
