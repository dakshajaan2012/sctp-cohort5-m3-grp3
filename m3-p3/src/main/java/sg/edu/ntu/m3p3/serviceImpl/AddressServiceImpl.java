package sg.edu.ntu.m3p3.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.exception.UserNotFoundException;
import sg.edu.ntu.m3p3.repository.AddressRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;
import sg.edu.ntu.m3p3.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
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

    @Override
    public Address createAddressForUser(UUID userId, Address address) {
        return userRepository.findById(userId).map(user -> {
            address.setUser(user);
            logger.info("Creating address for userId: {}", userId);
            return addressRepository.save(address);
        }).orElseThrow(() -> {
            logger.error("User not found with id: {}", userId);
            return new UserNotFoundException("User not found with id " + userId);
        });
    }

}
