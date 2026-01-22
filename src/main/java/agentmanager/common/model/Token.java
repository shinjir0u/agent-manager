package agentmanager.common.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	public void generateToken() {
		String token = UUID.randomUUID().toString();
		Long expirationDate = Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli();

		this.value = token;
		this.expiration = expirationDate;
	}

}
