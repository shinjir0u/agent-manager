package agentmanager.backoffice.repository.filter;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.backoffice.model.QAdmin;

public class AdminFilter {

	public static BooleanExpression withUsername(String username) {
		return QAdmin.admin.username.like(username);
	}

	public static BooleanExpression withEmail(String email) {
		return QAdmin.admin.email.like(email);
	}

}
