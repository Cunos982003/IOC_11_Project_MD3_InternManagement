package re.edu.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentRequest {
    // User fields (for creating new user)
    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mã sinh viên không được để trống")
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private Double gpa;
    private String major;
    private String className;
    private Integer enrollmentYear;
}
