package re.edu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import re.edu.dto.request.UpdateRoleRequest;
import re.edu.dto.request.UpdateStatusRequest;
import re.edu.dto.request.UpdateUserRequest;
import re.edu.dto.request.UserRequest;
import re.edu.dto.response.UserResponse;
import re.edu.entity.User;
import re.edu.exception.ConflictException;
import re.edu.exception.ForbiddenException;
import re.edu.exception.ResourceNotFoundException;
import re.edu.mapper.UserMapper;
import re.edu.repository.UserRepository;
import re.edu.service.UserService;
import re.edu.util.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers(Role role) {
        List<User> users = (role != null)
                ? userRepository.findByRole(role)
                : userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(findById(id));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isActive(true)
                .build();
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = findById(id);
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ConflictException("Email đã tồn tại");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateStatus(Long id, UpdateStatusRequest request) {
        User user = findById(id);
        user.setActive(request.getIsActive());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateRole(Long targetId, UpdateRoleRequest request, String currentUsername) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
        User targetUser = findById(targetId);

        if (targetUser.getRole() == Role.ADMIN && !currentUser.getId().equals(targetId)) {
            throw new ForbiddenException("Không được phép thay đổi quyền của ADMIN khác");
        }
        targetUser.setRole(request.getRole());
        return userMapper.toResponse(userRepository.save(targetUser));
    }

    @Override
    public void deleteUser(Long id, String currentUsername) {
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại"));
        if (currentUser.getId().equals(id)) {
            throw new ForbiddenException("Không thể tự xóa tài khoản của mình");
        }
        findById(id);
        userRepository.deleteById(id);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại với ID: " + id));
    }
}
