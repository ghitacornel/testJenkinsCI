package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("no person found for id " + id));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void save(Person person) {
        repository.save(person);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
