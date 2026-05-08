# Internship Management System — Development Checklist

> Thứ tự thực hiện: Foundation → Security → Entities → Services → Controllers → Testing

---

## Phase 0 — Project Setup

- [ ] Thêm dependency thiếu vào `build.gradle`:
  - [ ] `spring-boot-starter-validation` (cho `@Valid`)
  - [ ] `io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson` (JWT)
  - [ ] Xóa `spring-boot-starter-thymeleaf` (REST API không cần)
- [ ] Cấu hình `application.yml` (xóa `application.properties` hoặc chuyển sang yml):
  - [ ] DataSource: `url`, `username`, `password` (PostgreSQL)
  - [ ] JPA: `ddl-auto=update`, `show-sql=true`
  - [ ] JWT: `secret`, `expiration` (86400000ms = 24h)
  - [ ] Server port
- [ ] Đổi base package từ `re.edu` sang `re.edu.intershipms` (theo README)
- [ ] Tạo cấu trúc package:
  ```
  re.edu.intershipms/
  ├── config/
  ├── controller/
  ├── dto/request/ & dto/response/
  ├── entity/
  ├── repository/
  ├── service/interface/ & service/impl/
  ├── mapper/
  ├── security/
  └── util/
  ```

---

## Phase 1 — Foundation (Cross-cutting concerns)

### 1.1 Enums
- [ ] `Role` enum: `ADMIN`, `MENTOR`, `STUDENT`
- [ ] `UserStatus` enum: `ACTIVE`, `INACTIVE`
- [ ] `AssignmentStatus` enum: `PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

### 1.2 Standard Response Wrapper
- [ ] `ApiResponse<T>` — fields: `success`, `message`, `data`, `errors`, `timestamp`
- [ ] `PaginatedData<T>` — fields: `items`, `pagination` (currentPage, pageSize, totalPages, totalItems)
- [ ] `FieldError` — fields: `field`, `message`
- [ ] Static factory methods: `ApiResponse.success(data)`, `ApiResponse.error(message, errors)`

### 1.3 Global Exception Handler (`@RestControllerAdvice`)
- [ ] `400` — `MethodArgumentNotValidException` → validation errors array
- [ ] `401` — `AuthenticationException` / expired JWT
- [ ] `403` — `AccessDeniedException`
- [ ] `404` — custom `ResourceNotFoundException`
- [ ] `409` — custom `ConflictException`
- [ ] `500` — generic `Exception` fallback

### 1.4 Custom Exceptions
- [ ] `ResourceNotFoundException`
- [ ] `ConflictException`
- [ ] `ForbiddenException`

---

## Phase 2 — Security & JWT

- [ ] `JwtUtil`: `generateToken(user)`, `validateToken(token)`, `extractUsername(token)`
- [ ] `JwtAuthenticationFilter` (`OncePerRequestFilter`): parse header → validate → set `SecurityContext`
- [ ] `UserDetailsServiceImpl`: load user by username (email) from DB
- [ ] `SecurityConfig`:
  - [ ] Permit: `POST /api/auth/login`
  - [ ] Authenticate mọi request còn lại
  - [ ] Stateless session (`SessionCreationPolicy.STATELESS`)
  - [ ] Đăng ký `JwtAuthenticationFilter` trước `UsernamePasswordAuthenticationFilter`
  - [ ] `PasswordEncoder` bean (`BCryptPasswordEncoder`)
  - [ ] `AuthenticationManager` bean

---

## Phase 3 — Entities (JPA)

- [ ] `User` — `id`, `username`, `email`, `password`, `role(Role)`, `isActive`, `createdAt`, `updatedAt`
- [ ] `Student` — `id`, `user(OneToOne→User)`, `studentCode`, `fullName`, `dateOfBirth`, `phone`, `address`, `gpa`, ...
- [ ] `Mentor` — `id`, `user(OneToOne→User)`, `fullName`, `department`, `phone`, `specialization`, ...
- [ ] `InternshipPhase` — `id`, `name`, `description`, `startDate`, `endDate`, `status`
- [ ] `EvaluationCriteria` — `id`, `name`, `description`, `maxScore`
- [ ] `AssessmentRound` — `id`, `name`, `phase(ManyToOne)`, `startDate`, `endDate`
- [ ] `RoundCriteria` — `id`, `round(ManyToOne)`, `criteria(ManyToOne)`, `weight`
- [ ] `InternshipAssignment` — `id`, `student(ManyToOne)`, `mentor(ManyToOne)`, `phase(ManyToOne)`, `status(AssignmentStatus)`, `assignedAt`
- [ ] `AssessmentResult` — `id`, `assignment(ManyToOne)`, `roundCriteria(ManyToOne)`, `mentor(ManyToOne)`, `score`, `comment`, `evaluatedAt`
- [ ] Kiểm tra tất cả `@Table`, `@Column` constraints, unique index

---

## Phase 4 — Repositories

- [ ] `UserRepository` — `findByEmail`, `findByUsername`, `existsByEmail`
- [ ] `StudentRepository` — `findByUserId`, `findAllByMentorId` (qua Assignment)
- [ ] `MentorRepository` — `findByUserId`
- [ ] `InternshipPhaseRepository`
- [ ] `EvaluationCriteriaRepository`
- [ ] `AssessmentRoundRepository` — `findByPhaseId`
- [ ] `RoundCriteriaRepository` — `findByRoundId`
- [ ] `InternshipAssignmentRepository` — `findByStudentId`, `findByMentorId`, `findByPhaseId`
- [ ] `AssessmentResultRepository` — `findByAssignmentId`, `findByMentorId`

---

## Phase 5 — DTOs

- [ ] `LoginRequest` (`email`, `password`) + `LoginResponse` (`token`, `tokenType`, `user`)
- [ ] `UserRequest` / `UserResponse`
- [ ] `StudentRequest` / `StudentResponse`
- [ ] `MentorRequest` / `MentorResponse`
- [ ] `InternshipPhaseRequest` / `InternshipPhaseResponse`
- [ ] `EvaluationCriteriaRequest` / `EvaluationCriteriaResponse`
- [ ] `AssessmentRoundRequest` / `AssessmentRoundResponse`
- [ ] `RoundCriteriaRequest` / `RoundCriteriaResponse`
- [ ] `InternshipAssignmentRequest` / `InternshipAssignmentResponse`
- [ ] `AssessmentResultRequest` / `AssessmentResultResponse`
- [ ] `UpdateStatusRequest` (`status`), `UpdateRoleRequest` (`role`)

---

## Phase 6 — Mappers

- [ ] `UserMapper` — `toResponse(User)`, `toEntity(UserRequest)`
- [ ] `StudentMapper`
- [ ] `MentorMapper`
- [ ] `InternshipPhaseMapper`
- [ ] `EvaluationCriteriaMapper`
- [ ] `AssessmentRoundMapper`
- [ ] `RoundCriteriaMapper`
- [ ] `InternshipAssignmentMapper`
- [ ] `AssessmentResultMapper`

---

## Phase 7 — APIs: Buổi 1 (BẮT BUỘC)

> Ưu tiên cao nhất — hoàn thành trước Buổi 1

### Auth Module
- [ ] `POST /api/auth/login` *(điểm: 2)* — xác thực, trả JWT token
- [ ] `GET /api/auth/me` *(điểm: 3)* — lấy profile từ JWT, trả `UserResponse`

### User Module (ADMIN only)
- [ ] `GET /api/users` *(điểm: 2)* — danh sách users, filter by `role` query param
- [ ] `GET /api/users/{user_id}` *(điểm: 2)* — chi tiết user theo ID
- [ ] `POST /api/users` *(điểm: 2)* — tạo user mới, hash password, check email trùng → 409
- [ ] `PUT /api/users/{user_id}` *(điểm: 2)* — cập nhật thông tin cơ bản
- [ ] `PUT /api/users/{user_id}/status` *(điểm: 2)* — toggle `isActive`
- [ ] `PUT /api/users/{user_id}/role` *(điểm: 3)* — đổi role; **ADMIN không được đổi role của ADMIN khác**
- [ ] `DELETE /api/users/{user_id}` *(điểm: 3)* — xóa user; không được tự xóa chính mình

### Student Module
- [ ] `GET /api/students` *(điểm: 2)* — ADMIN xem tất cả; MENTOR chỉ xem sinh viên được phân công
- [ ] `GET /api/students/{student_id}` *(điểm: 2)* — STUDENT chỉ xem của mình
- [ ] `POST /api/students` *(điểm: 3)* — ADMIN tạo, phải liên kết với User có `role=STUDENT`
- [ ] `PUT /api/students/{student_id}` *(điểm: 2)* — STUDENT chỉ sửa của mình

### Mentor Module
- [ ] `GET /api/mentors` *(điểm: 2)* — ADMIN, STUDENT xem; STUDENT xem thông tin chung (ẩn sensitive fields)
- [ ] `GET /api/mentors/{mentor_id}` *(điểm: 2)* — MENTOR chỉ xem của mình
- [ ] `POST /api/mentors` *(điểm: 3)* — ADMIN tạo, phải liên kết với User có `role=MENTOR`
- [ ] `PUT /api/mentors/{mentor_id}` *(điểm: 2)* — MENTOR chỉ sửa của mình

---

## Phase 8 — APIs: Buổi 2 (BẮT BUỘC + TÙY CHỌN)

### Internship Phase (BẮT BUỘC)
- [ ] `GET /api/internship_phases` *(điểm: 3)* — ALL roles
- [ ] `GET /api/internship_phases/{phase_id}` *(điểm: 2)*
- [ ] `POST /api/internship_phases` *(điểm: 2)* — ADMIN only
- [ ] `PUT /api/internship_phases/{phase_id}` *(điểm: 2)* — ADMIN only
- [ ] `DELETE /api/internship_phases/{phase_id}` *(điểm: 2)* — ADMIN only

### Evaluation Criteria (BẮT BUỘC)
- [ ] `GET /api/evaluation_criteria` *(điểm: 2)* — ALL roles
- [ ] `GET /api/evaluation_criteria/{criterion_id}` *(điểm: 2)*
- [ ] `POST /api/evaluation_criteria` *(điểm: 2)* — ADMIN only
- [ ] `PUT /api/evaluation_criteria/{criterion_id}` *(điểm: 2)*
- [ ] `DELETE /api/evaluation_criteria/{criterion_id}` *(điểm: 2)*

### Assessment Round (TÙY CHỌN)
- [ ] `GET /api/assessment_rounds` *(điểm: 2)* — filter by `phase_id` query param
- [ ] `GET /api/assessment_rounds/{round_id}` *(điểm: 2)*
- [ ] `POST /api/assessment_rounds` *(điểm: 2)* — kèm danh sách `criterion_id` + `weight`
- [ ] `PUT /api/assessment_rounds/{round_id}` *(điểm: 2)*
- [ ] `DELETE /api/assessment_rounds/{round_id}` *(điểm: 3)* — kiểm tra ràng buộc trước khi xóa

### Round Criteria (TÙY CHỌN)
- [ ] `GET /api/round_criteria` *(điểm: 2)* — filter by `round_id`
- [ ] `GET /api/round_criteria/{round_criterion_id}` *(điểm: 2)*
- [ ] `POST /api/round_criteria` *(điểm: 3)* — thêm criterion vào round với `weight`
- [ ] `PUT /api/round_criteria/{round_criterion_id}` *(điểm: 2)* — cập nhật `weight`
- [ ] `DELETE /api/round_criteria/{round_criterion_id}` *(điểm: 3)*

---

## Phase 9 — APIs: Buổi 3 (TÙY CHỌN)

### Internship Assignment
- [ ] `GET /api/internship_assignments` *(điểm: 2)* — lọc theo role (ADMIN all, MENTOR của mình, STUDENT của mình)
- [ ] `GET /api/internship_assignments/{assignment_id}` *(điểm: 3)* — lọc quyền tương tự
- [ ] `POST /api/internship_assignments` *(điểm: 3)* — ADMIN gán student cho mentor trong phase
- [ ] `PUT /api/internship_assignments/{assignment_id}/status` *(điểm: 2)* — ADMIN cập nhật trạng thái

### Assessment Result
- [ ] `GET /api/assessment_results` *(điểm: 3)* — filter by `user_id`, `assignment_id`; lọc quyền
- [ ] `POST /api/assessment_results` *(điểm: 2)* — MENTOR chỉ đánh giá sinh viên được phân công
- [ ] `PUT /api/assessment_results/{result_id}` *(điểm: 2)* — MENTOR chỉ sửa kết quả do mình tạo

---

## Phase 10 — Business Rules Checklist

- [ ] JWT token phải gửi kèm `Authorization: Bearer <token>` header
- [ ] ADMIN không được đổi `role` của ADMIN khác
- [ ] ADMIN không được tự xóa chính mình
- [ ] STUDENT chỉ xem/sửa dữ liệu của chính mình
- [ ] MENTOR chỉ xem sinh viên được phân công (qua `InternshipAssignment`)
- [ ] MENTOR chỉ tạo/sửa `AssessmentResult` của sinh viên mình được phân công
- [ ] `POST /api/students` — user liên kết phải có `role=STUDENT`
- [ ] `POST /api/mentors` — user liên kết phải có `role=MENTOR`
- [ ] Tổng `weight` của các `RoundCriteria` trong một round nên = 100 (validate)
- [ ] Không xóa Phase/Round khi đang có Assignment/Result liên quan (409 Conflict)

---

## Phase 11 — Response Format Compliance

- [ ] Mọi response đều wrap trong `ApiResponse<T>`
- [ ] `POST` trả `201 Created`; `GET`, `PUT`, `DELETE` trả `200 OK`
- [ ] Danh sách có phân trang trả `PaginatedData<T>` trong field `data`
- [ ] Lỗi validation trả mảng `errors` với `field` + `message`
- [ ] Field `timestamp` luôn có mặt trong response
- [ ] Không bao giờ trả trực tiếp `Entity` ra ngoài — luôn dùng `DTO`

---

## Phase 12 — Testing (Postman)

Mỗi endpoint cần tối thiểu 3 test case:

- [ ] **Happy path** — input hợp lệ, data trả về đúng
- [ ] **Invalid input** — thiếu field bắt buộc, sai format, ID không tồn tại (404)
- [ ] **Auth/Role** — gọi không có token (401), sai role (403)
- [ ] **Conflict/Business** — (nếu có) vi phạm ràng buộc nghiệp vụ (409)

### Test flow đề xuất (theo thứ tự):
1. `POST /api/auth/login` → lấy token ADMIN
2. Tạo users (MENTOR, STUDENT)
3. Tạo student profile, mentor profile
4. Tạo internship phase
5. Tạo evaluation criteria
6. Tạo assessment round + round criteria
7. Tạo internship assignment
8. MENTOR login → tạo assessment result
9. STUDENT login → xem kết quả

---

## Phase 13 — Clean Code Checklist

- [ ] Không trả `Entity` trực tiếp từ Controller
- [ ] Mỗi Service có `interface` + `impl` tách biệt
- [ ] Dùng `@Valid` trên `@RequestBody` trong Controller
- [ ] Dùng `enum` cho `role`, `status` — không dùng String cứng
- [ ] Tất cả constant nằm trong `util/Constants.java`
- [ ] Không có business logic trong Controller (chỉ delegate cho Service)
- [ ] Không có DB query trong Controller hoặc Entity
- [ ] Tên method Service rõ ràng: `getAllStudents()`, `createMentor()`, `updateUserStatus()`
- [ ] Exception được throw từ Service, xử lý tại `GlobalExceptionHandler`
- [ ] OpenAPI / Swagger tự động qua `springdoc-openapi` (đã có dependency)

---

## Tổng điểm tham khảo

| Nhóm | Bắt buộc | Tùy chọn |
|---|---|---|
| Buổi 1 | 17 endpoints | — |
| Buổi 2 | 10 endpoints | 10 endpoints |
| Buổi 3 | — | 7 endpoints |
| **Tổng** | **27 endpoints** | **17 endpoints** |
