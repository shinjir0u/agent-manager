package agentmanager.api;

import agentmanager.api.model.DogDataCollection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DogApi {

	@GET("facts")
	Call<DogDataCollection> getDogData(@Query(value = "limit") Integer limit);

}
