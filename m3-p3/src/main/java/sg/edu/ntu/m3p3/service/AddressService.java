package sg.edu.ntu.m3p3.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.User.User;

@Service
public interface AddressService {
	Address saveAddress(Address address);

	List<Address> findAllAddresses();

	Address getAddressById(Long id);

	Address updateAddress(Address address);

	void deleteAddressById(Long id);

	 List<Address> findAddressesByUser(User user);

	 List<Address> findAddressesByUserId(UUID userId);

	 List<Address> findAddressesByUserIdAndAlias(UUID userId, String alias);

	 Optional<Address> getAddressByIdAndUserId(Long addressId, UUID userId);
}
