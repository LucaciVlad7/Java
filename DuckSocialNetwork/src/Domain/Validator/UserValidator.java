package Domain.Validator;
import Domain.entities.User;
import Domain.entities.User;
import Domain.entities.Duck;
import Domain.entities.Person;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User>{
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_]{3,20}$"
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"
    );

    @Override
    public void validate(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }

        validateCommonFields(user);

        // Type-specific validation
        if (user instanceof Duck) {
            validateDuck((Duck) user);
        } else if (user instanceof Person) {
            validatePerson((Person) user);
        }
    }

    private void validateCommonFields(User user) {
        // Validate username
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username cannot be empty");
        }
        if (!USERNAME_PATTERN.matcher(user.getUsername()).matches()) {
            throw new ValidationException("Username must be 3-20 characters long and contain only letters, numbers, and underscores");
        }

        // Validate email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new ValidationException("Invalid email format");
        }

        // Validate password
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }
        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            throw new ValidationException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit");
        }

        // Validate ID
        if (user.getId() == null) {
            throw new ValidationException("User ID cannot be null");
        }
        if (user.getId() <= 0) {
            throw new ValidationException("User ID must be a positive number");
        }
    }

    private void validateDuck(Duck duck) {
        // Validate duck type
        if (duck.getType() == null) {
            throw new ValidationException("Duck type cannot be null");
        }

        // Validate speed
        if (duck.getSpeed() < 0) {
            throw new ValidationException("Duck speed cannot be negative");
        }
        if (duck.getSpeed() > 100) {
            throw new ValidationException("Duck speed cannot exceed 100 km/h");
        }

        // Validate resistance
        if (duck.getResistance() < 1 || duck.getResistance() > 10) {
            throw new ValidationException("Duck resistance must be between 1 and 10");
        }

        // Validate performance makes sense
        double calculatedPerformance = (duck.getSpeed() * 0.6) + (duck.getResistance() * 0.4);
        double actualPerformance = duck.getPerformance();
        if (Math.abs(calculatedPerformance - actualPerformance) > 0.01) {
            throw new ValidationException("Duck performance calculation is inconsistent");
        }
    }

    private void validatePerson(Person person) {
        // Validate first name
        if (person.getFirstName() == null || person.getFirstName().trim().isEmpty()) {
            throw new ValidationException("First name cannot be empty");
        }
        if (person.getFirstName().length() < 2 || person.getFirstName().length() > 50) {
            throw new ValidationException("First name must be between 2 and 50 characters long");
        }

        // Validate last name
        if (person.getLastName() == null || person.getLastName().trim().isEmpty()) {
            throw new ValidationException("Last name cannot be empty");
        }
        if (person.getLastName().length() < 2 || person.getLastName().length() > 50) {
            throw new ValidationException("Last name must be between 2 and 50 characters long");
        }

        // Validate birth date
        if (person.getBirthDate() == null) {
            throw new ValidationException("Birth date cannot be null");
        }
        if (person.getBirthDate().isAfter(java.time.LocalDate.now())) {
            throw new ValidationException("Birth date cannot be in the future");
        }
        if (person.getAge() < 0 || person.getAge() > 100) {
            throw new ValidationException("Age must be between 0 and 150 years");
        }

        // Validate job
        if (person.getJob() == null || person.getJob().trim().isEmpty()) {
            throw new ValidationException("Job cannot be empty");
        }

        // Validate empathy level
        if (person.getEmpatyLevel() < 1 || person.getEmpatyLevel() > 10) {
            throw new ValidationException("Empathy level must be between 1 and 10");
        }
    }
}