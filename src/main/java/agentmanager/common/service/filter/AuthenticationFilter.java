package agentmanager.common.service.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import agentmanager.common.model.principal.PrincipalObject;
import agentmanager.common.model.token.Token;
import agentmanager.common.service.token.TokenService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		String[] excludedPaths = new String[] { "/health", "/admin/login", "/sale_executive/login" };
		return Arrays.stream(excludedPaths).anyMatch(excludedPath -> excludedPath.equals(path));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String authenticationHeader = request.getHeader("Authorization");
			String tokenValue = null;
			if (authenticationHeader != null && authenticationHeader.startsWith("Bearer"))
				tokenValue = authenticationHeader.substring(7);

			if (tokenValue != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				Token token = tokenService.getTokenByValue(tokenValue);

				if (token != null) {
					String authorityType = token.extractAuthority();

					List<GrantedAuthority> authorities = new ArrayList<>();
					if (authorityType != "NONE")
						authorities.add(new SimpleGrantedAuthority(authorityType));

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							new PrincipalObject(token.extractId()), null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		} catch (NullPointerException exception) {
			handleFilterException(response, exception);
		}
	}

	private void handleFilterException(HttpServletResponse response, Exception e) throws IOException {

		// 1. Set the headers
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", LocalDateTime.now().toString());
		errorBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		errorBody.put("error", "Unauthorized");
		errorBody.put("message", e.getMessage());

		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = mapper.writeValueAsString(errorBody);

		response.getWriter().write(jsonResponse);

	}

}
