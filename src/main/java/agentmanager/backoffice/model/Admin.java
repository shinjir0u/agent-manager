package agentmanager.backoffice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	public Admin(String username, String email, String password) {

		this.username = username;
		this.email = email;
		this.password = encodingPassword(password);
	}

	public String encodingPassword(String password) {

		if (password == null) {

		}

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {

		if (this.password != null && !this.password.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(this.password);
			setPassword(encodedPassword);
		}

	}

	public void update(String email) {

		if (email == null) {

		}

		this.email = email;
	}

}
