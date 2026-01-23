package agentmanager.common.model.token;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import agentmanager.common.model.authority.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "tokens")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "token")
	private String value;

	private Long expiration;

	@JsonIgnore
	public Boolean isTokenExpired() {
		return Instant.now().toEpochMilli() > this.expiration;
	}

	public void generateToken(Long id) {
		this.generateToken(id, null);
	}

	public void generateToken(Long id, UserAuthority authority) {
		String authorityType = authority == null ? "NONE" : authority.toString();

		String token = UUID.randomUUID().toString() + "." + id + "." + authorityType;
		Long expirationDate = Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli();

		this.value = Base64.getEncoder().encodeToString(token.getBytes());
		this.expiration = expirationDate;
	}

	private String extractData(Integer dataNumber) {
		if (this.value == null || this.value.isEmpty())
			return null;

		String plainToken = new String(Base64.getDecoder().decode(this.value));
		return plainToken.split("\\.")[dataNumber];
	}

	public Long extractId() {
		return Long.valueOf(this.extractData(1));
	}

	public String extractAuthority() {
		return this.extractData(2);
	}

}
