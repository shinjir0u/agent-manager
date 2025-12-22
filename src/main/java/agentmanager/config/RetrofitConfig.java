package agentmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

	@Bean
	OkHttpClient client() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(Level.BODY);

		return new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
	}

	@Bean
	Retrofit retrofit() {
		return new Retrofit.Builder().baseUrl("https://dogapi.dog/api/v2").client(client())
				.addConverterFactory(JacksonConverterFactory.create()).build();
	}
}
