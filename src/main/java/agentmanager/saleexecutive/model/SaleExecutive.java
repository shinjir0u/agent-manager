package agentmanager.saleexecutive.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@TypeDef(name = "status_enum", typeClass = PostgresEnumType.class)
public class SaleExecutive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String email;

	private String password;

	@Column(name = "phone_number")
	@JsonProperty("phone_number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Type(type = "status_enum")
	@Column(columnDefinition = "sale_executive_status")
	private Status status = Status.ACTIVE;

	@OneToMany(mappedBy = "saleExecutive", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Registration> registrations = new ArrayList<>();

	public SaleExecutive(String username, String email, String password, String phoneNumber) {

		this.username = username;
		this.email = email;
		this.password = encodingPassword(password);
		this.phoneNumber = phoneNumber;

	}

	public String encodingPassword(String password) {

		if (password == null) {
			throw new NullPointerException("Password cannot be null");
		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);
	}

	public void update(String email, String phoneNumber) {

		if (email == null) {
			throw new NullPointerException("Email cannot be null");
		}

		if (phoneNumber == null)
			throw new NullPointerException("Phone number cannot be null");

		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public void transferRegistrations(SaleExecutive saleExecutiveToReceive) {
		saleExecutiveToReceive.getRegistrations().addAll(this.registrations);

		for (Registration registration : this.registrations)
			registration.setSaleExecutive(saleExecutiveToReceive);

		this.registrations = new ArrayList<>();
	}

	public void terminate() {
		this.status = Status.TERMINATED;
	}

}
