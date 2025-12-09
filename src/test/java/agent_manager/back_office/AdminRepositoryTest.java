package agent_manager.back_office;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/admins")).andExpect(status().isOk());
	}

	@Test
	public void testGetUser() throws Exception {
		mockMvc.perform(get("/admins/1")).andExpectAll(status().isOk(), jsonPath("$.username").value("admin1"),
				jsonPath("$.email").value("admin1@example.com"));
	}

}
