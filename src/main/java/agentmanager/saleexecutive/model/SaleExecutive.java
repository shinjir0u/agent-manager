package agentmanager.saleexecutive.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "sale_executive_id")
	private List<Registration> registrations = new ArrayList<>();

	public void encodePassword(PasswordEncoder passwordEncoder) {

		if (this.password != null && !this.password.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(this.password);
			setPassword(encodedPassword);
		}

	}

}
