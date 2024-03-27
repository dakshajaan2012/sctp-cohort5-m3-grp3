package sg.edu.ntu.m3p3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByCity(String city);

	Address findByAlias(String alias);
}
