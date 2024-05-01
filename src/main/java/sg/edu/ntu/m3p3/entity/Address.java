package sg.edu.ntu.m3p3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sg.edu.ntu.m3p3.entity.User.User;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@Data
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "Postal code cannot be blank")
    @Pattern(regexp = "^[0-9]{5,6}$", message = "Invalid postal code format")
	@Column(name = "postal_code")
	private String postalCode;

	@NotBlank(message = "Unit number cannot be blank")
	@Column(name = "unit_number")
	private String unitNumber;

	@NotBlank(message = "Street cannot be blank")
	@Column(name = "street")
	private String Street;

	@NotBlank(message = "City cannot be blank")
	@Column(name = "city")
	private String city = "Singapore";

	@NotBlank(message = "Country cannot be blank")
	@Column(name = "country")
	private String country = "Singapore";

	@Column(name = "is_favorite")
	private boolean isFavorite;

	@Pattern(regexp = "^(|home|office|shopping_mall|school)$", message = "Alias must be either 'home', 'office', 'shopping_mall', 'school', or blank")
	@Column(name = "alias")
	private String alias;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
}
