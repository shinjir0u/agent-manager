package agentmanager.common.model;

import java.time.Instant;

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

	public Token(String value) {
		this.value = value;
	}

	public String extractId() {
		if (this.value == null || this.value.isEmpty())
			return null;
		return extractData(1);
	}

	@JsonIgnore
	public Long extractExpiration() {
		return Long.valueOf(extractData(2));
	}

	@JsonIgnore
	public Boolean isTokenExpired() {
		return Instant.now().toEpochMilli() > this.extractExpiration();
	}

	private String extractData(Integer dataNumber) {
		if (this.value == null || this.value.isEmpty())
			return null;
		String[] valueArray = this.value.split("\\.");
		if (valueArray.length > dataNumber)
			return valueArray[dataNumber];
		return null;
	}

}
