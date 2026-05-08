package re.edu.service;

import re.edu.dto.request.UpdateRoleRequest;
import re.edu.dto.request.UpdateStatusRequest;
import re.edu.dto.request.UpdateUserRequest;
import re.edu.dto.request.UserRequest;
import re.edu.dto.response.UserResponse;
import re.edu.util.Role;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers(Role role);
    UserResponse getUserById(Long id);
    UserResponse createUser(UserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse updateStatus(Long id, UpdateStatusRequest request);
    UserResponse updateRole(Long targetId, UpdateRoleRequest request, String currentUsername);
    void deleteUser(Long id, String currentUsername);
}
