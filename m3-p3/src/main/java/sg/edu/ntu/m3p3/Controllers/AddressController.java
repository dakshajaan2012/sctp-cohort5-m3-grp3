package sg.edu.ntu.m3p3.Controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.service.AddressService;
import sg.edu.ntu.m3p3.service.UserService;

@RestController
@RequestMapping("/addresses")
@CrossOrigin
public class AddressController {
	private final AddressService addressService;
	private final UserService userService;

	private final Logger logger = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	public AddressController(AddressService addressService, UserService userService) {
		this.addressService = addressService;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Address> createAddress(@RequestBody Address address) {
		Address savedAddress = addressService.saveAddress(address);
		return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Address>> getAllAddresses() {
		List<Address> addresses = addressService.findAllAddresses();
		return new ResponseEntity<>(addresses, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable("id") Long id) {
		Address address = addressService.getAddressById(id);
		if (address != null) {
			return new ResponseEntity<>(address, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAddress(@PathVariable("id") Long id, @RequestBody Address addressDetails) {
		Address currentAddress = addressService.getAddressById(id);
		if (currentAddress != null) {

			currentAddress.setPostalCode(addressDetails.getPostalCode());
			currentAddress.setUnitNumber(addressDetails.getUnitNumber());
			currentAddress.setStreet(addressDetails.getStreet());
			currentAddress.setCity(addressDetails.getCity());
			currentAddress.setCountry(addressDetails.getCountry());
			currentAddress.setFavorite(addressDetails.isFavorite());
			currentAddress.setAlias(addressDetails.getAlias());
			Address updatedAddress = addressService.updateAddress(currentAddress);
			return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id) {
		Address address = addressService.getAddressById(id);
		if (address != null) {
			addressService.deleteAddressById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{userId}/address/{addressId}")
	public ResponseEntity<Address> getUserAddressById(@PathVariable UUID userId, @PathVariable Long addressId) {
		if (!userService.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Address> address = addressService.getAddressByIdAndUserId(addressId, userId);
		return address.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/{userId}/address")
	public ResponseEntity<List<Address>> getUserAddressesByAlias(@PathVariable UUID userId,
			@RequestParam String alias) {
		if (!userService.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Address> addresses = addressService.findAddressesByUserIdAndAlias(userId, alias);
		return ResponseEntity.ok(addresses);
	}
}
