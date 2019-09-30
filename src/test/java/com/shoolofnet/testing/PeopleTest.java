package com.shoolofnet.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shoolofnet.testing.controller.PeopleController;
import com.shoolofnet.testing.entity.Person;
import com.shoolofnet.testing.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PeopleController.class) //classe que o mockMvc vai se basear para fazer as requisições
public class PeopleTest {

	@Autowired
	private MockMvc mock;
	
	@MockBean //classe que sera mockada
	private PeopleService peopleService;
	
	@Test
	public void findAll() throws Exception {
		final Person person = Person.builder()
				.id(1L)
				.name("Mauricio")
				.age(22)
				.build();

		final List<Person> mockPeople = Collections.singletonList(person);

		final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		final String mockPeopleJSON = ow.writeValueAsString(mockPeople);
		
		when(this.peopleService.findAll()).thenReturn(mockPeople);
		this.mock.perform(get("/people")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().is(200))
		.andExpect(content().json(mockPeopleJSON));
	}
	
	@Test
	public void createNewPerson() throws Exception {
		final Person person = Person.builder()
				.id(1L)
				.name("Mauricio")
				.age(22)
				.build();
		
		when(this.peopleService.create(any(Person.class))).thenReturn(person);
		
		final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		final String mockPersonJSON = ow.writeValueAsString(person);
		
		this.mock.perform(post("/people")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.content(mockPersonJSON))
		.andExpect(status().isOk())
		.andExpect(content().json(mockPersonJSON));

		verify(this.peopleService).create(any(Person.class));
	}

	@Test
	public void removePerson() throws Exception {
		mock.perform(delete("/people" + "/{id}", 2L))
				.andExpect(status().is(200));

		verify(this.peopleService).remove(eq(2L));
	}
	
	@Test
	public void createNewPersonAndFail() throws Exception {
		final Person person = Person.builder()
				.id(1L)
				.age(22)
				.build();
		
		when(peopleService.create(any(Person.class))).thenReturn(person);
		
		final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		final String json = ow.writeValueAsString(person);
		
		mock.perform(post("/people")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(json))
		.andExpect(status().is(400));
	}
}