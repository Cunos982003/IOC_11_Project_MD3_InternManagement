package re.edu.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import re.edu.util.Role;

@Getter
@Setter
public class UpdateRoleRequest {
    @NotNull(message = "Vai trò không được để trống")
    private Role role;
}
