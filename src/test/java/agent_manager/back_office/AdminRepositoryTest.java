package agent_manager.back_office;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import agent_manager.config.TestConfig;
import agent_manager.config.WebConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WebConfig.class, TestConfig.class })
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class AdminRepositoryTest {

	@Autowired
	private WebApplicationContext context;

	private ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/admins")).andExpectAll(status().isOk(), jsonPath("$").isArray(),
				jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetUser() throws Exception {
		mockMvc.perform(get("/admins/1")).andExpectAll(status().isOk(), jsonPath("$.username").value("admin1"),
				jsonPath("$.email").value("admin1@example.com"), jsonPath("$.password").exists());
	}

	@Test
	public void testCreateUserJson() throws Exception {
		Admin adminToAdd = Admin.builder().username("user").email("user@gmail.com").password("123").build();
		String adminJson = objectMapper.writeValueAsString(adminToAdd);
		mockMvc.perform(post("/admins").contentType(MediaType.APPLICATION_JSON).content(adminJson)).andExpectAll(
				status().isCreated(), jsonPath("$.username").value("user"), jsonPath("$.email").value("user@gmail.com"),
				jsonPath("$.password").exists(), jsonPath("$.id").exists());
	}

	@Test
	public void testCreateUserForm() throws Exception {
		mockMvc.perform(post("/admins").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("username", "user")
				.param("email", "user@gmail.com").param("password", "123")).andExpectAll(status().isCreated(),
						jsonPath("$.username").value("user"), jsonPath("$.email").value("user@gmail.com"),
						jsonPath("$.password").exists(), jsonPath("$.id").exists());
	}

	@Test
	public void testUpdateUserJson() throws Exception {
		testGetUser();

		Admin adminToUpdate = Admin.builder().id(1L).username("user").email("user@gmail.com").password("123").build();
		String adminJson = objectMapper.writeValueAsString(adminToUpdate);
		mockMvc.perform(put("/admins/1").contentType(MediaType.APPLICATION_JSON).content(adminJson))
				.andExpect(status().isOk());

		mockMvc.perform(get("/admins/1")).andExpectAll(status().isOk(), jsonPath("$.username").value("user"),
				jsonPath("$.email").value("user@gmail.com"), jsonPath("$.password").exists());
	}

	@Test
	public void testUpdateUserForm() throws Exception {
		testGetUser();

		mockMvc.perform(put("/admins/1").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("username", "user")
				.param("email", "user@gmail.com").param("password", "123")).andExpect(status().isOk());

		mockMvc.perform(get("/admins/1")).andExpectAll(status().isOk(), jsonPath("$.username").value("user"),
				jsonPath("$.email").value("user@gmail.com"), jsonPath("$.password").exists());
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(delete("/admins/1")).andExpect(status().isNoContent());

		mockMvc.perform(get("/admins/1")).andExpectAll(status().isNotFound(),
				jsonPath("$").value("No such admin with id: 1"));
	}

}
