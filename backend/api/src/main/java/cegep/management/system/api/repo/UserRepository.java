package cegep.management.system.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cegep.management.system.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> findByEmail(String email);
}
