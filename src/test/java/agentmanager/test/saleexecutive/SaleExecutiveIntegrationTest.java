package agentmanager.test.saleexecutive;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import agentmanager.config.PersistenceConfig;
import agentmanager.config.WebConfig;
import agentmanager.saleexecutive.model.SaleExecutive;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WebConfig.class, PersistenceConfig.class })
@WebAppConfiguration
public class SaleExecutiveIntegrationTest {

	@Autowired
	private WebApplicationContext context;

	private ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetSaleExecutives() throws Exception {
		mockMvc.perform(get("/sale_executives")).andExpectAll(status().isOk(), jsonPath("$").isArray());
	}

	@Test
	public void testGetSaleExecutive() throws Exception {
//		(username, email, password, phone_number) 'dse001', 'dse001@example.com', 'pw123', '09410000001'
		mockMvc.perform(get("/sale_executives/1")).andExpectAll(status().isOk(), jsonPath("$.username").value("dse001"),
				jsonPath("$.email").value("dse001@example.com"), jsonPath("$.password").exists(),
				jsonPath("$.phoneNumber").value("09410000001"));
	}

	@Test
	public void testCreateSaleExecutiveJson() throws Exception {
		SaleExecutive saleExecutiveToAdd = SaleExecutive.builder().username("test").email("test@gmail.com").
		mockMvc.perform(post("/sale_executives").contentType(MediaType.APPLICATION_JSON).content()).andExpectAll(status().isCreated(), json)
	}

}
