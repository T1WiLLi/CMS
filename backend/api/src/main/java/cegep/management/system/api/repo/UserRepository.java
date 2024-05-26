package cegep.management.system.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import cegep.management.system.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
