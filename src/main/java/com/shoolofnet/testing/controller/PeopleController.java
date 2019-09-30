package com.shoolofnet.testing.controller;

import com.shoolofnet.testing.entity.Person;
import com.shoolofnet.testing.service.PeopleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class PeopleController {
	
	private final PeopleService peopleService;

	@GetMapping
	public List<Person> findAll() {
		return this.peopleService.findAll();
	}
	
	@PostMapping
	public Person create(@RequestBody @Valid final Person person) {
		return this.peopleService.create(person);
	}
	
	@DeleteMapping("/{id}")
	public void remove(@PathVariable("id") final Long id) {
		this.peopleService.remove(id);
	}
}