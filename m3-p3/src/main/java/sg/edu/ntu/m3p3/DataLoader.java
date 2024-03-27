package sg.edu.ntu.m3p3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.AddressRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;

@Component
public class DataLoader {
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	
	@Autowired
	public DataLoader(AddressRepository addressRepository, UserRepository userRepository) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	@PostConstruct
	public void loadDate() {
		String[] aliases = { "home", "office", "shopping_mall", "school", "" };
        
		User dummyUser = new User();
		dummyUser.setUserName("johndoe");
		dummyUser.setFirstName("John");
		dummyUser.setLastName("Doe");
		dummyUser.setEmail("johndoe@example.com");
		dummyUser.setPassword("Password123");
		dummyUser.setIsActive(true);

		dummyUser = userRepository.save(dummyUser);

		addressRepository.deleteAll();

		Address address1 = new Address();
		address1.setPostalCode("123456");
		address1.setUnitNumber("01-01");
		address1.setStreet("123 Orchard Road");
		address1.setCity("Singapore");
		address1.setCountry("Singapore");
		address1.setFavorite(true);
		address1.setAlias(aliases[0]);
		address1.setUser(dummyUser);

		Address address2 = new Address();
		address2.setPostalCode("654321");
		address2.setUnitNumber("02-02");
		address2.setStreet("456 Sentosa Cove");
		address2.setCity("Singapore");
		address2.setCountry("Singapore");
		address2.setFavorite(false);
		address2.setAlias(aliases[1]);
		address2.setUser(dummyUser);

		Address address3 = new Address();
		address3.setPostalCode("789012");
		address3.setUnitNumber("03-03");
		address3.setStreet("789 Marina Bay Sands");
		address3.setCity("Singapore");
		address3.setCountry("Singapore");
		address3.setFavorite(false);
		address3.setAlias(aliases[2]);
		address3.setUser(dummyUser);

		Address address4 = new Address();
		address4.setPostalCode("210987");
		address4.setUnitNumber("04-04");
		address4.setStreet("210 Jurong East");
		address4.setCity("Singapore");
		address4.setCountry("Singapore");
		address4.setFavorite(false);
		address4.setAlias(aliases[3]);
		address4.setUser(dummyUser);

		addressRepository.save(address1);
		addressRepository.save(address2);
		addressRepository.save(address3);
		addressRepository.save(address4);
	}
}