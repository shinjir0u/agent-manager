package agentmanager.saleexecutive.repository.filter;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.saleexecutive.model.QSaleExecutive;
import agentmanager.saleexecutive.model.Status;

public class SaleExecutiveFilter {

	public static BooleanExpression withUsername(String username) {
		return QSaleExecutive.saleExecutive.username.contains(username);
	}

	public static BooleanExpression withEmail(String email) {
		return QSaleExecutive.saleExecutive.email.contains(email);
	}

	public static BooleanExpression withPhoneNumber(String phoneNumber) {
		return QSaleExecutive.saleExecutive.phoneNumber.eq(phoneNumber);
	}

	public static BooleanExpression withStatus(Status status) {
		return QSaleExecutive.saleExecutive.status.eq(status);
	}

}
