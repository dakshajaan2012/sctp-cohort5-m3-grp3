package sg.edu.ntu.m3p3.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.AddressRepository;

@SpringBootTest
public class AddressServiceImplTest {
	@Mock
	private AddressRepository addressRepository;

	@InjectMocks
	private AddressServiceImpl addressService;

	private User testUser;
	private Address address1;
	private Address address2;
	private UUID userId;

	@BeforeEach
	void setUp() {
		testUser = new User();
		userId = UUID.randomUUID();
		testUser.setUserId(userId);
		testUser.setUserName("testUser");
		testUser.setFirstName("test");
		testUser.setLastName("Test");
		testUser.setEmail("Test@example.com");
		testUser.setPassword("Password123");
		testUser.setIsActive(true);

		address1 = new Address();
		address1.setPostalCode("123456");
		address1.setUnitNumber("01-01");
		address1.setStreet("123 Orchard Road");
		address1.setCity("Singapore");
		address1.setCountry("Singapore");
		address1.setFavorite(true);
		address1.setAlias("home");
		address1.setUser(testUser);

		address2 = new Address();
		address2.setPostalCode("654321");
		address2.setUnitNumber("02-02");
		address2.setStreet("456 Sentosa Cove");
		address2.setCity("Singapore");
		address2.setCountry("Singapore");
		address2.setFavorite(false);
		address2.setAlias("school");
		address2.setUser(testUser);
	}

	@Test
	void testFindAddressesByUserId() {
		List<Address> mockAddresses = Arrays.asList(address1, address2);
		when(addressRepository.findByUser_UserId(userId)).thenReturn(mockAddresses);

		List<Address> addresses = addressService.findAddressesByUserId(userId);

		assertNotNull(addresses);
		assertEquals(2, addresses.size(), "Should fetch two addresses");
		verify(addressRepository).findByUser_UserId(userId);
	}

	@Test
	void testFindAddressesByUserIdAndAlias() {
		String alias = "home";
		List<Address> mockAddresses = Arrays.asList(address1);
		when(addressRepository.findByUser_UserIdAndAlias(userId, alias)).thenReturn(mockAddresses);

		List<Address> addresses = addressService.findAddressesByUserIdAndAlias(userId, alias);

		assertNotNull(addresses);
		assertEquals(1, addresses.size(), "Should fetch one address with alias 'home'");
		verify(addressRepository).findByUser_UserIdAndAlias(userId, alias);
	}
}
