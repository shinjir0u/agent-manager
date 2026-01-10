package agentmanager.common.service.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import agentmanager.common.model.Token;
import agentmanager.common.service.TokenService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authenticationHeader = request.getHeader("Authorization");
		String tokenValue = null;
		if (authenticationHeader != null && authenticationHeader.startsWith("Bearer"))
			tokenValue = authenticationHeader.substring(7);

		if (tokenValue != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			Token token = new Token(tokenValue);
			String id = token.extractId();

			if (tokenService.validateToken(token.getValue(), id)) {
				List<GrantedAuthority> roles = new ArrayList<>();
				roles.add(new SimpleGrantedAuthority("SALE_EXECUTIVE"));
				if (id.startsWith("a"))
					roles.add(new SimpleGrantedAuthority("ADMIN"));

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id,
						null, roles);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
