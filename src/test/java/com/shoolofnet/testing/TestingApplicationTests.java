package com.shoolofnet.testing;

import com.shoolofnet.testing.controller.HelloWorldController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloWorldController.class)
public class TestingApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() {

	}

	@Test
	public void testHelloWorld() throws Exception {
		this.mockMvc
				.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World"));
	}

}
