package com.shoolofnet.testing.service;

import com.shoolofnet.testing.entity.Person;
import com.shoolofnet.testing.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__({ @Lazy}))
public class PeopleService {

	private final PersonRepository personRepository;
	
	public Person create(final Person person) {
		return this.personRepository.save(person);
	}

	public List<Person> findAll() {
		return this.personRepository.findAll();
	}

	public void remove(final Long id) {
		if (this.personRepository.existsById(id)) {
			this.personRepository.deleteById(id);
		}
	}

}