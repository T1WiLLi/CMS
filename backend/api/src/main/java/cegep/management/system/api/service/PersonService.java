package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.repo.PersonRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final PersonRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PersonService(PersonRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<Person> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<Person> getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Person createUser(Person user) {
        String hashedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return this.userRepository.save(user);
    }

    @Transactional
    public Person updateUser(Long id, Person userDetails) {
        return this.userRepository.findById(id)
                .map(user -> {
                    String hashedPassword = passwordEncoder.encode(userDetails.getPassword());
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setEmail(userDetails.getEmail());
                    user.setPhone(userDetails.getPhone());
                    user.setPassword(hashedPassword);
                    user.setDateOfBirth(userDetails.getDateOfBirth());
                    return this.userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        if (this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
    }

    public boolean authenticateUser(String email, String plainTextPassword) {
        Person user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
        return passwordEncoder.matches(plainTextPassword, user.getPassword());
    }
}
