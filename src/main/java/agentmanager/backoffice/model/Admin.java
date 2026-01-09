package agentmanager.backoffice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import agentmanager.common.model.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String email;

	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token", referencedColumnName = "id")
	private Token token;

	public Admin(String username, String email, String password) {

		this.username = username;
		this.email = email;
		this.password = encodingPassword(password);
	}

	public String encodingPassword(String password) {

		if (password == null) {
			throw new NullPointerException("Password cannot be null");
		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);
	}

	public void update(String email) {

		if (email == null) {
			throw new NullPointerException("Email cannot be null");
		}

		this.email = email;
	}

}
