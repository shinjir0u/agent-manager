package agentmanager.saleexecutive.repository.filter;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.saleexecutive.model.QSaleExecutive;
import agentmanager.saleexecutive.model.SaleExecutiveStatus;

public class SaleExecutiveFilter {

	public static BooleanExpression withUsername(String username) {
		if (username == null || username.isEmpty())
			return null;
		return QSaleExecutive.saleExecutive.username.like(username);
	}

	public static BooleanExpression withEmail(String email) {
		if (email == null || email.isEmpty())
			return null;
		return QSaleExecutive.saleExecutive.email.containsIgnoreCase(email);
	}

	public static BooleanExpression withPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty())
			return null;
		return QSaleExecutive.saleExecutive.phoneNumber.eq(phoneNumber);
	}

	public static BooleanExpression withStatus(SaleExecutiveStatus status) {
		if (status == null)
			return null;
		return QSaleExecutive.saleExecutive.status.eq(status);
	}

}
