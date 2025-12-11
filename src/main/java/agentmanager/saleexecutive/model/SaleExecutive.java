package agentmanager.saleexecutive.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import agentmanager.registration.model.Registration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "sale_executives")
public class SaleExecutive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String email;

	private String password;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@OneToMany
	@JoinColumn(name = "sale_executive_id")
	private List<Registration> registrations;

	public void encodePassword(PasswordEncoder passwordEncoder) {

		if (this.password != null && !this.password.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(this.password);
			setPassword(encodedPassword);
		}

	}

}
