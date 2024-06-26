package sg.edu.ntu.m3p3.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.exception.UserNotFoundException;
import sg.edu.ntu.m3p3.service.AddressService;
import sg.edu.ntu.m3p3.service.UserService;

@RestController
@Tag(name = "2. Address", description = "Address APIs")
@RequestMapping("/addresses")
@CrossOrigin
public class AddressController {
	private final AddressService addressService;
	private final UserService userService;

	@Autowired
	public AddressController(AddressService addressService, UserService userService) {
		this.addressService = addressService;
		this.userService = userService;
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

	@GetMapping("/{userId}/address")
	public ResponseEntity<List<Address>> getUserAddressByUserId(@PathVariable UUID userId) {
		if (!userService.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Address> addresses = addressService.findAddressesByUserId(userId);
		return ResponseEntity.ok(addresses);
	}

	@GetMapping("/{userId}/address/")
	public ResponseEntity<List<Address>> getAddressByUserIdAndAlias(@PathVariable UUID userId,
			@RequestParam String alias) {
		List<Address> addresses = addressService.findAddressesByUserIdAndAlias(userId, alias);
		if (addresses.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(addresses);
	}

	@GetMapping("/{userId}/address/{addressId}")
	public ResponseEntity<Address> getUserAddressById(@PathVariable UUID userId, @PathVariable Long addressId) {
		if (!userService.existsById(userId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<Address> address = addressService.getAddressByIdAndUserId(addressId, userId);
		return address.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/{userId}/favorites")
	public ResponseEntity<List<Address>> getUserFavoriteAddresses(@PathVariable UUID userId) {
		List<Address> favoriteAddresses = addressService.findUserFavoriteAddresses(userId);
		if (favoriteAddresses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(favoriteAddresses);
	}

	@PostMapping
	public ResponseEntity<Address> addAddressForUser(@RequestParam UUID userId, @RequestBody @Valid Address address) {
		try {
			Address savedAddress = addressService.createAddressForUser(userId, address);
			return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
