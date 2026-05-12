package re.edu.util;

public class Constants {

    // JWT Constants
    public static final long JWT_EXPIRATION = 900000L; // 15 minutes in milliseconds
    public static final String JWT_SECRET_KEY = "your_jwt_secret_key_here";

    // Validation Messages
    public static final String VALIDATION_USERNAME_REQUIRED = "Username is required";
    public static final String VALIDATION_PASSWORD_REQUIRED = "Password is required";
    public static final String VALIDATION_PASSWORD_MIN_LENGTH = "Password must be at least 6 characters";
    public static final String VALIDATION_EMAIL_REQUIRED = "Email is required";
    public static final String VALIDATION_EMAIL_INVALID = "Email format is invalid";
    public static final String VALIDATION_NAME_REQUIRED = "Name is required";
    public static final String VALIDATION_START_DATE_REQUIRED = "Start date is required";
    public static final String VALIDATION_END_DATE_REQUIRED = "End date is required";
    public static final String VALIDATION_DATE_RANGE = "Start date must be before end date";
    public static final String VALIDATION_MAX_SCORE_POSITIVE = "Max score must be greater than 0";
    public static final String VALIDATION_WEIGHT_RANGE = "Weight must be between 0 and 100";

    // Error Messages
    public static final String ERROR_USERNAME_EXISTS = "Username already exists";
    public static final String ERROR_EMAIL_EXISTS = "Email already exists";
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String ERROR_PHASE_NOT_FOUND = "Internship phase not found";
    public static final String ERROR_CRITERIA_NOT_FOUND = "Evaluation criteria not found";
    public static final String ERROR_PHASE_COMPLETED = "Cannot modify completed phase";
    public static final String ERROR_PHASE_HAS_ACTIVE_ROUNDS = "Cannot delete phase with active rounds";
    public static final String ERROR_CRITERIA_IN_USE = "Cannot delete criteria that is being used";
    public static final String ERROR_UNAUTHORIZED = "Unauthorized access";
    public static final String ERROR_FORBIDDEN = "Access forbidden";

    // Success Messages
    public static final String SUCCESS_REGISTRATION = "User registered successfully";
    public static final String SUCCESS_LOGIN = "Login successful";
    public static final String SUCCESS_PHASE_CREATED = "Internship phase created successfully";
    public static final String SUCCESS_PHASE_UPDATED = "Internship phase updated successfully";
    public static final String SUCCESS_PHASE_DELETED = "Internship phase deleted successfully";
    public static final String SUCCESS_CRITERIA_CREATED = "Evaluation criteria created successfully";
    public static final String SUCCESS_CRITERIA_UPDATED = "Evaluation criteria updated successfully";
    public static final String SUCCESS_CRITERIA_DELETED = "Evaluation criteria deleted successfully";

    private Constants() {
    }
}
