package agent_manager.sale_executive;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import agent_manager.common.entity.User;
import agent_manager.registration.Registration;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sale_executives")
public class SaleExecutive extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("phone_number")
	private String phoneNumber;

	@OneToMany
	@JoinColumn(name = "sale_executive_id")
	private List<Registration> registrations;

}
