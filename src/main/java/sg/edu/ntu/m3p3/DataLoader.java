package sg.edu.ntu.m3p3;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.edu.ntu.m3p3.entity.Address;
import sg.edu.ntu.m3p3.entity.Session;
import sg.edu.ntu.m3p3.entity.UserLog;
import sg.edu.ntu.m3p3.entity.Feature.Action;
import sg.edu.ntu.m3p3.entity.Feature.Element;
import sg.edu.ntu.m3p3.entity.Feature.Feature;
import sg.edu.ntu.m3p3.entity.Feature.FeatureName;
import sg.edu.ntu.m3p3.entity.FeatureUsage.FeatureUsage;
import sg.edu.ntu.m3p3.entity.User.User;
import sg.edu.ntu.m3p3.repository.AddressRepository;
import sg.edu.ntu.m3p3.repository.FeatureRepository;
import sg.edu.ntu.m3p3.repository.FeatureUsageRepository;
import sg.edu.ntu.m3p3.repository.SessionRepository;
import sg.edu.ntu.m3p3.repository.UserLogRepository;
import sg.edu.ntu.m3p3.repository.UserRepository;

@Component
public class DataLoader {
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	private final UserLogRepository userLogRepository;
	private final SessionRepository sessionRepository;
	private final FeatureRepository featureRepository;
	private final FeatureUsageRepository featureUsageRepository;

	public DataLoader(AddressRepository addressRepository, UserRepository userRepository,
			UserLogRepository userLogRepository, SessionRepository sessionRepository,
			FeatureRepository featureRepository, FeatureUsageRepository featureUsageRepository) {
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
		this.userLogRepository = userLogRepository;
		this.sessionRepository = sessionRepository;
		this.featureRepository = featureRepository;
		this.featureUsageRepository = featureUsageRepository;
	}

	@PostConstruct
	public void loadData() {
		String[] aliases = { "home", "office", "shopping_mall", "school", "" };
		String[] firstNames = { "John", "Johnny", "Emmy", "Emma", "Daniel", "Jayne", "James", "Jamie", "David",
				"Emily" };
		String[] lastNames = { "Tan", "Lim", "Chan" };
		String[] emailDomains = { "@hotmail.com", "@gmail.com", "@gov.sg" };

		for (int i = 1; i <= 10; i++) {
			// Create and save user
			User user = createUser(firstNames, lastNames, emailDomains, i);

			// Create and save addresses for the user (30 addresses in total)
			for (int j = 1; j <= 3; j++) {
				createAddressesForUser(user, aliases);
			}

			// Create and save user logs (50 logs in total)
			for (int j = 1; j <= 5; j++) {
				createUserLog(user, "127.0.0." + ((i - 1) * 10 + j), "Web");
			}

			// Create and save sessions (50 sessions in total)
			for (int j = 1; j <= 5; j++) {
				createSession(user, "Session " + ((i - 1) * 10 + j));
			}
		}

		// Load features and usages
		loadFeaturesAndUsages();
	}

	private User createUser(String[] firstNames, String[] lastNames, String[] emailDomains, int index) {
		// Generate a random index for selecting first and last names
		Random random = new Random();
		int firstNameIndex = random.nextInt(firstNames.length);
		int lastNameIndex = random.nextInt(lastNames.length);
		int emailIndex = random.nextInt(emailDomains.length);

		String firstName = firstNames[firstNameIndex];
		String lastName = lastNames[lastNameIndex];
		String userName = firstName.toLowerCase() + "_" + lastName.toLowerCase();
		String password = "pass" + firstName + index;
		String email = userName + emailDomains[emailIndex];
		boolean randomIsActive = new Random().nextBoolean();
		boolean randomIsAdmin = new Random().nextBoolean();
		LocalDateTime createdAt = getRandomDateTimeIn2024();

		User user = new User();
		user.setUserName(userName);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setIsActive(randomIsActive);
		user.setIsAdmin(randomIsAdmin);
		user.setCreatedAt(createdAt);
		user.setUpdatedAt(createdAt);
		return userRepository.save(user);
	}

	private void createAddressesForUser(User user, String[] aliases) {
		for (int i = 0; i < aliases.length; i++) {
			Address address = new Address();
			address.setPostalCode(generateRandomPostalCode());
			address.setUnitNumber(generateRandomUnitNumber());
			address.setStreet("Street" + i);
			address.setCity("Singapore");
			address.setCountry("Singapore");
			address.setFavorite(i == 0); // First address is favorite
			address.setAlias(aliases[i]);
			address.setUser(user);
			addressRepository.save(address);
		}
	}

	private String generateRandomPostalCode() {
		// Generate a random 6-digit postal code
		return RandomStringUtils.randomNumeric(6);
	}

	private String generateRandomUnitNumber() {
		// Generate a random 3-digit number
		String firstPart = RandomStringUtils.randomNumeric(2);
		// Generate a random 3-digit number
		String secondPart = RandomStringUtils.randomNumeric(3);
		return firstPart + "-" + secondPart;
	}

	private void createUserLog(User user, String ipAddress, String origin) {
		LocalDateTime createdAt = getRandomDateTimeIn2024();
		UserLog userLog = new UserLog();
		userLog.setUser(user);
		userLog.setIpAddress(ipAddress);
		userLog.setOrigin(origin);
		userLog.setCreatedAt(createdAt);
		userLog.setUpdatedAt(createdAt);
		userLogRepository.save(userLog);
	}

	private void createSession(User user, String sessionName) {
		LocalDateTime createdAt = getRandomDateTimeIn2024();
		int randomHours = ThreadLocalRandom.current().nextInt(1, 8);
		LocalDateTime timeStop = createdAt.plusHours(randomHours);

		Session session = new Session();
		session.setUser(user);
		session.setCreatedAt(createdAt);
		session.setUpdatedAt(timeStop);
		sessionRepository.save(session);
	}

	private void loadFeaturesAndUsages() {
		LocalDateTime createdAt = getRandomDateTimeIn2024();
		// Load features
		for (int i = 1; i <= 20; i++) {
			Feature feature = new Feature();
			feature.setFeatureName(randomFeatureName());
			feature.setAction(randomAction());
			feature.setElement(randomElement());
			feature.setDeleted(false);
			feature.setCreatedAt(createdAt);
			feature.setUpdatedAt(createdAt);

			// Save feature
			featureRepository.save(feature);
		}

		List<Feature> allFeatures = featureRepository.findAll();
		Random randomFeature = new Random();

		List<Session> allSessions = sessionRepository.findAll();
		Random randomSession = new Random();

		// Load feature usages
		for (int i = 1; i <= 100; i++) {
			int randomFeatureIndex = randomFeature.nextInt(allFeatures.size());
			Feature r1 = allFeatures.get(randomFeatureIndex);

			int randomSessIndex = randomSession.nextInt(allSessions.size());
			Session r2 = allSessions.get(randomSessIndex);

			// Create and save feature usage
			FeatureUsage featureUsage = new FeatureUsage();
			featureUsage.setFeature(r1);
			featureUsage.setCreatedAt(LocalDateTime.now());
			featureUsage.setUpdatedAt(LocalDateTime.now());
			featureUsage.setDeleted(false);
			featureUsage.setSession(r2);
			featureUsage.setCreatedAt(createdAt);
			featureUsage.setUpdatedAt(createdAt);

			featureUsageRepository.save(featureUsage);
		}
	}

	private FeatureName randomFeatureName() {
		List<FeatureName> featureNames = Arrays.asList(FeatureName.values());
		return featureNames.get(new Random().nextInt(featureNames.size()));
	}

	private Action randomAction() {
		List<Action> actions = Arrays.asList(Action.values());
		return actions.get(new Random().nextInt(actions.size()));
	}

	private Element randomElement() {
		List<Element> elements = Arrays.asList(Element.values());
		return elements.get(new Random().nextInt(elements.size()));
	}

	private LocalDateTime getRandomDateTimeIn2024() {
		LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime end = LocalDateTime.now();
		long randomEpochSecond = ThreadLocalRandom.current().nextLong(start.toEpochSecond(ZoneOffset.UTC),
				end.toEpochSecond(ZoneOffset.UTC));
		return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, ZoneOffset.UTC);
	}
}
