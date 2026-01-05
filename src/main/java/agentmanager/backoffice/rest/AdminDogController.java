package agentmanager.backoffice.rest;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agentmanager.api.DogApi;
import agentmanager.api.model.DogDataCollection;
import agentmanager.common.service.RedisService;
import lombok.AllArgsConstructor;
import retrofit2.Call;
import retrofit2.Response;

@RestController
@RequestMapping("/admin/dog")
@AllArgsConstructor
public class AdminDogController {

	private final DogApi dogApi;

	private final RedisService redisService;

	@GetMapping("/facts")
	public DogDataCollection getDogDataCollection() throws IOException {
		if (redisService.getValue("dog") != null)
			return (DogDataCollection) redisService.getValue("dog");

		Call<DogDataCollection> call = dogApi.getDogData(3);
		Response<DogDataCollection> response = call.execute();

		if (!response.isSuccessful())
			return null;

		DogDataCollection data = response.body();
		redisService.setValue("dog", data);

		return data;
	}

}
