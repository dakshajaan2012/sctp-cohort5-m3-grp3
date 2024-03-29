package sg.edu.ntu.m3p3.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.AddressRepository;
import sg.edu.ntu.m3p3.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @SuppressWarnings("null")
    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElse(null);
    }

    @Override
    public void deleteAddressById(Long id) {
        addressRepository.deleteById((long) id);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> findAddressesByUserId(UUID userId) {
        return addressRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Address> findAddressesByUserIdAndAlias(UUID userId, String alias) {
        return addressRepository.findByUser_UserIdAndAlias(userId, alias);
    }

    @Override
    public Optional<Address> getAddressByIdAndUserId(Long addressId, UUID userId) {
        return addressRepository.findByIdAndUser_UserId(addressId, userId);
    }

    @Override
    public List<Address> findUserFavoriteAddresses(UUID userId) {
        return addressRepository.findByUser_UserIdAndIsFavorite(userId, true);
    }
}
