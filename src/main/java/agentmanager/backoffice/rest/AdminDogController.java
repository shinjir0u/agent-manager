package agentmanager.backoffice.rest;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agentmanager.api.DogApi;
import agentmanager.api.model.DogDataCollection;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RestController
@RequestMapping("/admin/dog")
@AllArgsConstructor
public class AdminDogController {

	private final DogApi dogApi;

	@GetMapping("/facts")
	public DogDataCollection getDogDataCollection() throws IOException {
		Call<DogDataCollection> call = dogApi.getDogData(3);
		Response<DogDataCollection> response = call.execute();
		if (response.isSuccessful())
			return response.body();
		else
			return null;
	}

}
