package agentmanager.backoffice.repository.filter;

import com.querydsl.core.types.dsl.BooleanExpression;

import agentmanager.backoffice.model.QAdmin;

public class AdminFilter {

	public static BooleanExpression withUsername(String username) {
		if (username == null || username.isEmpty())
			return null;
		return QAdmin.admin.username.containsIgnoreCase(username);
	}

	public static BooleanExpression withEmail(String email) {
		if (email == null || email.isEmpty())
			return null;
		return QAdmin.admin.email.containsIgnoreCase(email);
	}

}
