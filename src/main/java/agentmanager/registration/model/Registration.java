package agentmanager.registration.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import agentmanager.saleexecutive.model.SaleExecutive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "agent_name")
	@JsonProperty("agent_name")
	private String agentName;

	@Column(name = "phone_number")
	@JsonProperty("phone_number")
	private String phoneNumber;

	@Column(name = "registered_at")
	@JsonProperty(value = "registered_at")
	private Date registeredAt;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sale_executive_id")
	private SaleExecutive saleExecutive;

	public Registration(String agentName, String phoneNumber, SaleExecutive saleExecutive) {
		this.agentName = agentName;
		this.phoneNumber = phoneNumber;
		this.saleExecutive = saleExecutive;
		this.registeredAt = new Date();
	}

	public void update(String phoneNumber) {

		if (phoneNumber == null)
			throw new NullPointerException("Phone number cannot be null");

		this.phoneNumber = phoneNumber;

	}

}
