package sg.edu.ntu.m3p3.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.ntu.m3p3.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	List<Address> findByCity(String city);

	Address findByAlias(String alias);

	List<Address> findByUser_UserId(UUID userId);

	List<Address> findByUser_UserIdAndAlias(UUID userId, String alias);

	Optional<Address> findByIdAndUser_UserId(Long addressId, UUID userId);

	void deleteByUser_UserId(UUID userId);

	List<Address> findByUser_UserIdAndIsFavorite(UUID userId, boolean isFavorite);
}
