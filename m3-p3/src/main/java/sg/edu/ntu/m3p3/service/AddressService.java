package sg.edu.ntu.m3p3.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.Address;

@Service
public interface AddressService {
	Address saveAddress(Address address);

	List<Address> findAllAddresses();

	Address getAddressById(Long id);

	Address updateAddress(Address address);

	void deleteAddressById(Long id);

	List<Address> findAddressesByUserId(UUID userId);

	List<Address> findAddressesByUserIdAndAlias(UUID userId, String alias);

	Optional<Address> getAddressByIdAndUserId(Long addressId, UUID userId);

	List<Address> findUserFavoriteAddresses(UUID userId);
}
