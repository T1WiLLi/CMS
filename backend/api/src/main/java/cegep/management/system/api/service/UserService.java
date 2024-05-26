package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.User;
import cegep.management.system.api.repo.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> getUserByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public User createUser(User user) {
        String hashedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return this.userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return this.userRepository.findById(id)
                .map(user -> {
                    String hashedPassword = passwordEncoder.encode(userDetails.getPassword());
                    user.setName(userDetails.getName());
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
        return passwordEncoder.matches(plainTextPassword, user.getPassword());
    }
}
