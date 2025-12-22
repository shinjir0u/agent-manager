package agentmanager.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DogData {

	private String id;

	private String type;

	private DogDataAttributes attributes;

}
