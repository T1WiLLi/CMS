package cegep.management.system.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import cegep.management.system.api.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
