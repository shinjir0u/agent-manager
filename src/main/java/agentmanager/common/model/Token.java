package agentmanager.common.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	public Token(String value) {
		this.value = value;
	}

	public Long extractId() {
		if (this.value == null || this.value.isEmpty())
			return null;
		return extractData(1);
	}

	public Long extractExpiration() {
		return extractData(2);
	}

	public Boolean isTokenExpired() {
		return Instant.now().toEpochMilli() > this.extractExpiration();
	}

	private Long extractData(Integer dataNumber) {
		if (this.value == null || this.value.isEmpty())
			return null;
		String expiration = this.value.split(".")[dataNumber];
		return Long.valueOf(expiration);
	}

}
