# 🎓 Internship Management System

> Hệ thống quản lý thực tập sinh — xây dựng trên nền tảng Spring Boot 3.x với xác thực JWT và phân quyền theo vai trò.

---

## 📋 Mục lục

- [Giới thiệu](#-giới-thiệu)
- [Tính năng](#-tính-năng)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Cài đặt & Chạy dự án](#-cài-đặt--chạy-dự-án)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Phân quyền người dùng](#-phân-quyền-người-dùng)
- [API Reference](#-api-reference)
- [Chuẩn Response](#-chuẩn-response)
- [Mã trạng thái HTTP](#-mã-trạng-thái-http)
- [Test Case](#-test-case)
- [Clean Code Guidelines](#-clean-code-guidelines)
- [Contributing](#-contributing)

---

## 📖 Giới thiệu

**Internship Management System** là một REST API backend phục vụ quản lý toàn bộ quy trình thực tập trong môi trường giáo dục, bao gồm:

- Quản lý **giai đoạn thực tập** (Internship Phase)
- Quản lý **bảng tiêu chí đánh giá**
- Quản lý **đợt đánh giá** (Assessment Round)
- Chức năng **giáo viên hướng dẫn đánh giá sinh viên**
- Sinh viên **xem kết quả thực tập** theo giai đoạn / đợt

Hệ thống cung cấp **20 API endpoint** với tổng điểm **100 điểm**, trong đó **60 điểm** thuộc chức năng bắt buộc.

---

## ✨ Tính năng

| Module | Chức năng |
|---|---|
| 🗂️ Internship Phase | Tạo, sửa, xóa và quản lý giai đoạn thực tập |
| 📋 Assessment Criteria | Quản lý bảng tiêu chí đánh giá |
| 🔄 Assessment Round | Quản lý đợt đánh giá, phân công giáo viên |
| 👨‍🏫 Mentor Evaluation | Giáo viên đánh giá sinh viên theo đợt được phân công |
| 🎓 Student Results | Sinh viên xem kết quả đánh giá theo giai đoạn |
| 🔐 Authentication | Đăng nhập, cấp phát và xác thực JWT token |

---

## 🛠️ Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java 17+ |
| Framework | Spring Boot 3.x |
| ORM | Spring Data JPA + Hibernate |
| Cơ sở dữ liệu | PostgreSQL |
| Bảo mật | Spring Security + JWT |
| Build tool | Gradle |
| Test | Postman |
| Triển khai | Tomcat Server |

---

## 💻 Yêu cầu hệ thống

- **Java** 17 trở lên
- **PostgreSQL** 14+
- **Gradle** 7.x+

---

## 🚀 Cài đặt & Chạy dự án

### 1. Clone repository

```bash
git clone https://github.com/<your-username>/internship-management-system.git
cd internship-management-system
```

### 2. Cấu hình cơ sở dữ liệu

Tạo database PostgreSQL:

```sql
CREATE DATABASE internship_db;
```

Chỉnh sửa file `src/main/resources/application.properties`:

```properties
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/internship_db
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: your_jwt_secret_key
  expiration: 86400000  # 24 hours in ms
```

### 3. Build & Chạy

```bash
# Build dự án
./gradlew build

# Chạy ứng dụng
./gradlew bootRun
```

Ứng dụng sẽ khởi động tại: `http://localhost:8080`

### 4. Import Postman Collection

Import file collection Postman đính kèm (`/docs/postman_collection.json`) để test toàn bộ API.

---

## 📁 Cấu trúc dự án

```
src/main/java/com/example/intershipms/
│
├── config/             # JWT config, Security config, Global Exception Handler
├── controller/         # REST API endpoints
├── dto/                # Request & Response DTOs
├── entity/             # JPA Entities
├── repository/         # Spring Data JPA Repositories
├── service/            # Business logic (interface + implementation)
├── mapper/             # DTO <-> Entity mappers
└── util/               # Helper classes (Validation, Constants, Enums)
```

---

## 🔐 Phân quyền người dùng

Hệ thống có 3 vai trò với quyền hạn như sau:

| Vai trò | Mô tả quyền hạn |
|---|---|
| `ADMIN` | Tạo / sửa / xóa giai đoạn thực tập, đợt đánh giá, bảng tiêu chí |
| `MENTOR` | Đánh giá sinh viên theo đợt thực tập được phân công |
| `STUDENT` | Xem kết quả đánh giá thực tập theo giai đoạn |

> Xác thực thực hiện qua **JWT Bearer Token**. Mọi request cần gửi kèm header:
>
> ```
> Authorization: Bearer <token>
> ```

---

## 📡 API Reference

### Authentication

| Method | Endpoint | Mô tả | Quyền |
|---|---|---|---|
| `POST` | `/api/auth/login` | Đăng nhập, lấy JWT token | Public |
| `POST` | `/api/auth/register` | Đăng ký tài khoản | Public |

### Internship Phase

| Method | Endpoint | Mô tả | Quyền |
|---|---|---|---|
| `GET` | `/api/phases` | Lấy danh sách giai đoạn | ADMIN |
| `GET` | `/api/phases/{id}` | Lấy chi tiết giai đoạn | ADMIN |
| `POST` | `/api/phases` | Tạo giai đoạn mới | ADMIN |
| `PUT` | `/api/phases/{id}` | Cập nhật giai đoạn | ADMIN |
| `DELETE` | `/api/phases/{id}` | Xóa giai đoạn | ADMIN |

### Assessment Criteria

| Method | Endpoint | Mô tả | Quyền |
|---|---|---|---|
| `GET` | `/api/criteria` | Lấy danh sách tiêu chí | ADMIN |
| `POST` | `/api/criteria` | Tạo tiêu chí mới | ADMIN |
| `PUT` | `/api/criteria/{id}` | Cập nhật tiêu chí | ADMIN |
| `DELETE` | `/api/criteria/{id}` | Xóa tiêu chí | ADMIN |

### Assessment Round

| Method | Endpoint | Mô tả | Quyền |
|---|---|---|---|
| `GET` | `/api/rounds` | Lấy danh sách đợt đánh giá | ADMIN |
| `POST` | `/api/rounds` | Tạo đợt đánh giá mới | ADMIN |
| `PUT` | `/api/rounds/{id}` | Cập nhật đợt đánh giá | ADMIN |
| `DELETE` | `/api/rounds/{id}` | Xóa đợt đánh giá | ADMIN |

### Evaluation (Mentor)

| Method | Endpoint | Mô tả | Quyền |
|---|---|---|---|
| `GET` | `/api/evaluations/round/{roundId}` | Lấy danh sách đánh giá theo đợt | MENTOR |
| `POST` | `/api/evaluations` | Tạo đánh giá cho sinh viên | MENTOR |
| `PUT` | `/api/evaluations/{id}` | Cập nhật đánh giá | MENTOR |

### Student Results

| Method | Endpoint | Mô tả | Quyền |
|---|---|---|---|
| `GET` | `/api/results/phase/{phaseId}` | Xem kết quả theo giai đoạn | STUDENT |
| `GET` | `/api/results/round/{roundId}` | Xem kết quả theo đợt | STUDENT |

---

## 📦 Chuẩn Response

Tất cả API đều trả về theo cấu trúc thống nhất:

**Response thành công:**

```json
{
  "success": true,
  "message": "Thao tác thành công",
  "data": {},
  "errors": null,
  "timestamp": "2025-07-30T09:45:00"
}
```

**Response có phân trang:**

```json
{
  "success": true,
  "message": "Lấy danh sách thành công",
  "data": {
    "items": [
      { "id": 1, "name": "Item 1" },
      { "id": 2, "name": "Item 2" }
    ],
    "pagination": {
      "currentPage": 1,
      "pageSize": 10,
      "totalPages": 5,
      "totalItems": 50
    }
  },
  "errors": null,
  "timestamp": "2025-07-30T09:45:00"
}
```

**Response lỗi validation:**

```json
{
  "success": false,
  "message": "Dữ liệu không hợp lệ",
  "data": null,
  "errors": [
    { "field": "email", "message": "Email không hợp lệ" },
    { "field": "password", "message": "Mật khẩu phải tối thiểu 6 ký tự" }
  ],
  "timestamp": "2025-07-30T09:45:00"
}
```

---

## 📊 Mã trạng thái HTTP

| Status Code | Ý nghĩa | Tình huống |
|---|---|---|
| `200 OK` | Thành công | GET, PUT, DELETE thành công |
| `201 Created` | Tạo mới thành công | POST tạo tài nguyên mới |
| `400 Bad Request` | Dữ liệu không hợp lệ | Sai format, thiếu trường, validation lỗi |
| `401 Unauthorized` | Thiếu hoặc sai token | Chưa đăng nhập hoặc JWT không hợp lệ |
| `403 Forbidden` | Không có quyền | Truy cập sai vai trò |
| `404 Not Found` | Không tìm thấy | ID không tồn tại |
| `409 Conflict` | Xung đột nghiệp vụ | Đăng ký trùng, tên bị trùng |
| `500 Internal Server Error` | Lỗi hệ thống | Null pointer, exception không xử lý |

---

## 🧪 Test Case

Mỗi API bắt buộc phải có **tối thiểu 3 test case** theo các loại sau:

| Loại | Mô tả |
|---|---|
| ✅ Happy path | Input hợp lệ, kết quả trả về đúng kỳ vọng |
| ❌ Invalid input | Thiếu trường, sai format, ID không tồn tại |
| 🔒 Unauthorized / Role | Thiếu token (`401`), sai vai trò truy cập (`403`) |
| ⚠️ Conflict / Business | Trùng đăng ký, vi phạm ràng buộc nghiệp vụ |

---

## 🧹 Clean Code Guidelines

Dự án tuân thủ các nguyên tắc clean code sau:

- ✅ Tên hàm / biến rõ ràng, theo chuẩn `camelCase`
- ✅ Tuân thủ kiến trúc phân tầng, tránh lặp code (DRY)
- ✅ Mỗi `Service` tách rõ `interface` và `implementation`
- ✅ Dùng `@Valid`, `DTO` và `Mapper` để tách logic khỏi entity
- ✅ Không trả trực tiếp `Entity` ra ngoài — luôn dùng `DTO`
- ✅ Tách config JWT / Exception Handler thành package riêng
- ✅ Dùng `enum` thay cho chuỗi cứng (status, role, ...)
- ✅ Tuân thủ chuẩn REST API

---

## 🤝 Contributing

1. Fork repository này
2. Tạo branch mới: `git checkout -b feature/ten-tinh-nang`
3. Commit thay đổi: `git commit -m "feat: mô tả ngắn gọn"`
4. Push lên branch: `git push origin feature/ten-tinh-nang`
5. Tạo Pull Request và mô tả rõ thay đổi

---

<div align="center">
  <sub>Made with ❤️ — Internship Management System</sub>
</div>
