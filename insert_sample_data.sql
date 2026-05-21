-- ============================================
-- INSERT SAMPLE DATA
-- Mỗi bảng có ít nhất 5 bản ghi
-- Updated to match new entity structure
-- ============================================

-- ============================================
-- 1. USERS (10 users: 1 admin, 4 mentors, 5 students)
-- Password cho tất cả: "password123"
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- ============================================

INSERT INTO users (username, password, email, full_name, role, is_active, created_at, updated_at) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@edu.re', 'Administrator', 'ADMIN', true, NOW(), NOW()),

('mentor1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'huong.tran@edu.re', 'Trần Thị Hương', 'MENTOR', true, NOW(), NOW()),
('mentor2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'minh.le@edu.re', 'Lê Văn Minh', 'MENTOR', true, NOW(), NOW()),
('mentor3', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'lan.pham@edu.re', 'Phạm Thị Lan', 'MENTOR', true, NOW(), NOW()),
('mentor4', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'tuan.hoang@edu.re', 'Hoàng Văn Tuấn', 'MENTOR', true, NOW(), NOW()),

('student1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'an.nguyen@student.edu.re', 'Nguyễn Văn An', 'STUDENT', true, NOW(), NOW()),
('student2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'binh.tran@student.edu.re', 'Trần Thị Bình', 'STUDENT', true, NOW(), NOW()),
('student3', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'cuong.le@student.edu.re', 'Lê Văn Cường', 'STUDENT', true, NOW(), NOW()),
('student4', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'dung.pham@student.edu.re', 'Phạm Thị Dung', 'STUDENT', true, NOW(), NOW()),
('student5', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'em.hoang@student.edu.re', 'Hoàng Văn Em', 'STUDENT', true, NOW(), NOW());

-- ============================================
-- 2. STUDENTS (5 students)
-- student_id is now the PK (same as user_id)
-- Removed: gpa, fullName, phone
-- Added: dateOfBirth, address
-- Note: "class" is quoted because it's a reserved keyword
-- ============================================

INSERT INTO students (student_id, student_code, date_of_birth, address, major, "class", created_at, updated_at) VALUES
(6, 'SV2024001', '2002-03-15', '123 Nguyễn Huệ, Quận 1, TP.HCM', 'Công nghệ thông tin', 'K20CNTT01', NOW(), NOW()),
(7, 'SV2024002', '2002-05-20', '456 Lê Lợi, Quận 3, TP.HCM', 'Công nghệ thông tin', 'K20CNTT01', NOW(), NOW()),
(8, 'SV2024003', '2002-08-10', '789 Trần Hưng Đạo, Quận 5, TP.HCM', 'Khoa học máy tính', 'K20CNTT02', NOW(), NOW()),
(9, 'SV2024004', '2002-11-25', '321 Võ Văn Tần, Quận 3, TP.HCM', 'Khoa học máy tính', 'K20CNTT02', NOW(), NOW()),
(10, 'SV2024005', '2002-01-30', '654 Hai Bà Trưng, Quận 1, TP.HCM', 'Kỹ thuật phần mềm', 'K20CNTT03', NOW(), NOW());

-- ============================================
-- 3. MENTORS (4 mentors)
-- mentor_id is now the PK (same as user_id)
-- Removed: fullName
-- ============================================

INSERT INTO mentors (mentor_id, department, academic_rank, created_at, updated_at) VALUES
(2, 'Khoa Công nghệ thông tin', 'ThS', NOW(), NOW()),
(3, 'Khoa Công nghệ thông tin', 'TS', NOW(), NOW()),
(4, 'Khoa Khoa học máy tính', 'PGS.TS', NOW(), NOW()),
(5, 'Khoa Kỹ thuật phần mềm', 'ThS', NOW(), NOW());

-- ============================================
-- 4. INTERNSHIP_PHASES (5 phases)
-- Schema only has: PhaseID, PhaseName, StartDate, EndDate, Description
-- ============================================

INSERT INTO internship_phases (name, description, start_date, end_date, created_at, updated_at) VALUES
('Giai đoạn 1: Làm quen', 'Sinh viên làm quen với môi trường làm việc và công nghệ', '2024-01-15', '2024-02-15', NOW(), NOW()),
('Giai đoạn 2: Phát triển', 'Sinh viên tham gia phát triển tính năng thực tế', '2024-02-16', '2024-04-15', NOW(), NOW()),
('Giai đoạn 3: Hoàn thiện', 'Hoàn thiện sản phẩm và chuẩn bị báo cáo', '2024-04-16', '2024-05-31', NOW(), NOW()),
('Giai đoạn 4: Đánh giá', 'Đánh giá tổng kết và bảo vệ', '2024-06-01', '2024-06-15', NOW(), NOW()),
('Giai đoạn 5: Kết thúc', 'Hoàn tất thủ tục và nhận chứng nhận', '2024-06-16', '2024-06-30', NOW(), NOW());

-- ============================================
-- 5. EVALUATION_CRITERIA (7 criteria)
-- Removed: weight, isActive, criteria_type
-- ============================================

INSERT INTO evaluation_criteria (name, description, max_score, created_at, updated_at) VALUES
('Kỹ năng lập trình', 'Đánh giá khả năng code, debug và tối ưu hóa', 100, NOW(), NOW()),
('Làm việc nhóm', 'Khả năng phối hợp và giao tiếp trong team', 100, NOW(), NOW()),
('Giải quyết vấn đề', 'Tư duy logic và khả năng xử lý tình huống', 100, NOW(), NOW()),
('Chuyên cần', 'Thái độ làm việc, đúng giờ, trách nhiệm', 100, NOW(), NOW()),
('Sáng tạo', 'Khả năng đưa ra ý tưởng mới và cải tiến', 100, NOW(), NOW()),
('Kiến thức chuyên môn', 'Hiểu biết về công nghệ và domain', 100, NOW(), NOW()),
('Hoàn thành công việc', 'Mức độ hoàn thành task được giao', 100, NOW(), NOW());

-- ============================================
-- 6. ASSESSMENT_ROUNDS (6 rounds)
-- Removed: status
-- ============================================

INSERT INTO assessment_rounds (name, description, phase_id, start_date, end_date, is_active, created_at, updated_at) VALUES
('Đánh giá tuần 1', 'Đánh giá sau tuần đầu tiên làm quen', 1, '2024-01-22', '2024-01-26', true, NOW(), NOW()),
('Đánh giá tuần 4', 'Đánh giá cuối giai đoạn làm quen', 1, '2024-02-12', '2024-02-16', true, NOW(), NOW()),
('Đánh giá giữa kỳ', 'Đánh giá giữa giai đoạn phát triển', 2, '2024-03-15', '2024-03-22', true, NOW(), NOW()),
('Đánh giá cuối kỳ', 'Đánh giá cuối giai đoạn phát triển', 2, '2024-04-08', '2024-04-15', true, NOW(), NOW()),
('Đánh giá hoàn thiện', 'Đánh giá giai đoạn hoàn thiện sản phẩm', 3, '2024-05-20', '2024-05-27', false, NOW(), NOW()),
('Đánh giá tổng kết', 'Đánh giá tổng kết toàn bộ quá trình', 4, '2024-06-08', '2024-06-15', false, NOW(), NOW());

-- ============================================
-- 7. ROUND_CRITERIA (Mapping rounds to criteria)
-- Mỗi round có 5-7 tiêu chí
-- ============================================

INSERT INTO round_criteria (round_id, criterion_id, weight, created_at, updated_at) VALUES
-- Round 1: Đánh giá tuần 1 (5 criteria)
(1, 1, 0.30, NOW(), NOW()),
(1, 2, 0.25, NOW(), NOW()),
(1, 4, 0.25, NOW(), NOW()),
(1, 6, 0.15, NOW(), NOW()),
(1, 7, 0.05, NOW(), NOW()),

-- Round 2: Đánh giá tuần 4 (6 criteria)
(2, 1, 0.25, NOW(), NOW()),
(2, 2, 0.20, NOW(), NOW()),
(2, 3, 0.20, NOW(), NOW()),
(2, 4, 0.15, NOW(), NOW()),
(2, 6, 0.15, NOW(), NOW()),
(2, 7, 0.05, NOW(), NOW()),

-- Round 3: Đánh giá giữa kỳ (7 criteria - all)
(3, 1, 0.25, NOW(), NOW()),
(3, 2, 0.15, NOW(), NOW()),
(3, 3, 0.20, NOW(), NOW()),
(3, 4, 0.10, NOW(), NOW()),
(3, 5, 0.15, NOW(), NOW()),
(3, 6, 0.10, NOW(), NOW()),
(3, 7, 0.05, NOW(), NOW()),

-- Round 4: Đánh giá cuối kỳ (7 criteria - all)
(4, 1, 0.25, NOW(), NOW()),
(4, 2, 0.15, NOW(), NOW()),
(4, 3, 0.20, NOW(), NOW()),
(4, 4, 0.10, NOW(), NOW()),
(4, 5, 0.15, NOW(), NOW()),
(4, 6, 0.10, NOW(), NOW()),
(4, 7, 0.05, NOW(), NOW()),

-- Round 5: Đánh giá hoàn thiện (6 criteria)
(5, 1, 0.30, NOW(), NOW()),
(5, 3, 0.25, NOW(), NOW()),
(5, 5, 0.20, NOW(), NOW()),
(5, 6, 0.15, NOW(), NOW()),
(5, 7, 0.10, NOW(), NOW()),

-- Round 6: Đánh giá tổng kết (7 criteria - all)
(6, 1, 0.25, NOW(), NOW()),
(6, 2, 0.15, NOW(), NOW()),
(6, 3, 0.20, NOW(), NOW()),
(6, 4, 0.10, NOW(), NOW()),
(6, 5, 0.15, NOW(), NOW()),
(6, 6, 0.10, NOW(), NOW()),
(6, 7, 0.05, NOW(), NOW());

-- ============================================
-- 8. INTERNSHIP_ASSIGNMENTS (5 assignments)
-- Changed: assigned_at -> assigned_date
-- ============================================

INSERT INTO internship_assignments (student_id, mentor_id, phase_id, status, assigned_date, created_at, updated_at) VALUES
(6, 2, 1, 'IN_PROGRESS', '2024-01-15 08:00:00', NOW(), NOW()),
(7, 2, 1, 'IN_PROGRESS', '2024-01-15 08:00:00', NOW(), NOW()),
(8, 3, 1, 'IN_PROGRESS', '2024-01-15 08:00:00', NOW(), NOW()),
(9, 4, 1, 'IN_PROGRESS', '2024-01-15 08:00:00', NOW(), NOW()),
(10, 5, 1, 'IN_PROGRESS', '2024-01-15 08:00:00', NOW(), NOW());

-- ============================================
-- 9. ASSESSMENT_RESULTS (30+ results)
-- Mỗi assignment có kết quả cho round 1 và 2
-- ============================================

-- Assignment 1 (Student 6 - Nguyễn Văn An) - Round 1
INSERT INTO assessment_results (assignment_id, round_id, criterion_id, score, comments, evaluated_by, evaluation_date, created_at, updated_at) VALUES
(1, 1, 1, 85.0, 'Code tốt, cần cải thiện về tối ưu hóa', 2, '2024-01-25 10:30:00', NOW(), NOW()),
(1, 1, 2, 90.0, 'Làm việc nhóm rất tốt, hỗ trợ đồng đội nhiệt tình', 2, '2024-01-25 10:30:00', NOW(), NOW()),
(1, 1, 4, 95.0, 'Đi làm đúng giờ, có trách nhiệm cao', 2, '2024-01-25 10:30:00', NOW(), NOW()),
(1, 1, 6, 80.0, 'Kiến thức cơ bản tốt, cần học thêm về Spring Boot', 2, '2024-01-25 10:30:00', NOW(), NOW()),
(1, 1, 7, 88.0, 'Hoàn thành đầy đủ task được giao', 2, '2024-01-25 10:30:00', NOW(), NOW()),

-- Assignment 1 - Round 2
(1, 2, 1, 88.0, 'Có tiến bộ rõ rệt về kỹ năng code', 2, '2024-02-15 14:00:00', NOW(), NOW()),
(1, 2, 2, 92.0, 'Tiếp tục duy trì tinh thần teamwork tốt', 2, '2024-02-15 14:00:00', NOW(), NOW()),
(1, 2, 3, 85.0, 'Giải quyết vấn đề logic tốt', 2, '2024-02-15 14:00:00', NOW(), NOW()),
(1, 2, 4, 95.0, 'Chuyên cần xuất sắc', 2, '2024-02-15 14:00:00', NOW(), NOW()),
(1, 2, 6, 85.0, 'Đã nắm vững Spring Boot cơ bản', 2, '2024-02-15 14:00:00', NOW(), NOW()),
(1, 2, 7, 90.0, 'Hoàn thành tốt các task phức tạp', 2, '2024-02-15 14:00:00', NOW(), NOW()),

-- Assignment 2 (Student 7 - Trần Thị Bình) - Round 1
(2, 1, 1, 92.0, 'Kỹ năng React rất tốt', 2, '2024-01-25 11:00:00', NOW(), NOW()),
(2, 1, 2, 88.0, 'Giao tiếp tốt với team', 2, '2024-01-25 11:00:00', NOW(), NOW()),
(2, 1, 4, 90.0, 'Đúng giờ và có trách nhiệm', 2, '2024-01-25 11:00:00', NOW(), NOW()),
(2, 1, 6, 90.0, 'Kiến thức frontend vững', 2, '2024-01-25 11:00:00', NOW(), NOW()),
(2, 1, 7, 92.0, 'Hoàn thành xuất sắc', 2, '2024-01-25 11:00:00', NOW(), NOW()),

-- Assignment 2 - Round 2
(2, 2, 1, 94.0, 'Xuất sắc về React và TypeScript', 2, '2024-02-15 14:30:00', NOW(), NOW()),
(2, 2, 2, 90.0, 'Teamwork tốt', 2, '2024-02-15 14:30:00', NOW(), NOW()),
(2, 2, 3, 88.0, 'Giải quyết bug nhanh', 2, '2024-02-15 14:30:00', NOW(), NOW()),
(2, 2, 4, 92.0, 'Chuyên cần tốt', 2, '2024-02-15 14:30:00', NOW(), NOW()),
(2, 2, 6, 92.0, 'Kiến thức frontend rất vững', 2, '2024-02-15 14:30:00', NOW(), NOW()),
(2, 2, 7, 95.0, 'Hoàn thành xuất sắc mọi task', 2, '2024-02-15 14:30:00', NOW(), NOW()),

-- Assignment 3 (Student 8 - Lê Văn Cường) - Round 1
(3, 1, 1, 78.0, 'Cần cải thiện kỹ năng code', 3, '2024-01-25 15:00:00', NOW(), NOW()),
(3, 1, 2, 85.0, 'Làm việc nhóm tốt', 3, '2024-01-25 15:00:00', NOW(), NOW()),
(3, 1, 4, 88.0, 'Chuyên cần tốt', 3, '2024-01-25 15:00:00', NOW(), NOW()),
(3, 1, 6, 75.0, 'Kiến thức cơ bản cần củng cố', 3, '2024-01-25 15:00:00', NOW(), NOW()),
(3, 1, 7, 80.0, 'Hoàn thành đúng hạn', 3, '2024-01-25 15:00:00', NOW(), NOW()),

-- Assignment 3 - Round 2
(3, 2, 1, 82.0, 'Có tiến bộ về kỹ năng lập trình', 3, '2024-02-15 15:30:00', NOW(), NOW()),
(3, 2, 2, 87.0, 'Teamwork tốt hơn', 3, '2024-02-15 15:30:00', NOW(), NOW()),
(3, 2, 3, 80.0, 'Giải quyết vấn đề khá tốt', 3, '2024-02-15 15:30:00', NOW(), NOW()),
(3, 2, 4, 90.0, 'Chuyên cần tốt', 3, '2024-02-15 15:30:00', NOW(), NOW()),
(3, 2, 6, 80.0, 'Kiến thức đã cải thiện', 3, '2024-02-15 15:30:00', NOW(), NOW()),
(3, 2, 7, 85.0, 'Hoàn thành tốt task', 3, '2024-02-15 15:30:00', NOW(), NOW()),

-- Assignment 4 (Student 9 - Phạm Thị Dung) - Round 1
(4, 1, 1, 95.0, 'Kỹ năng Flutter xuất sắc', 4, '2024-01-25 16:00:00', NOW(), NOW()),
(4, 1, 2, 92.0, 'Làm việc nhóm rất tốt', 4, '2024-01-25 16:00:00', NOW(), NOW()),
(4, 1, 4, 95.0, 'Chuyên cần xuất sắc', 4, '2024-01-25 16:00:00', NOW(), NOW()),
(4, 1, 6, 93.0, 'Kiến thức mobile rất vững', 4, '2024-01-25 16:00:00', NOW(), NOW()),
(4, 1, 7, 95.0, 'Hoàn thành xuất sắc', 4, '2024-01-25 16:00:00', NOW(), NOW()),

-- Assignment 4 - Round 2
(4, 2, 1, 96.0, 'Xuất sắc về Flutter và Dart', 4, '2024-02-15 16:30:00', NOW(), NOW()),
(4, 2, 2, 94.0, 'Teamwork xuất sắc', 4, '2024-02-15 16:30:00', NOW(), NOW()),
(4, 2, 3, 92.0, 'Giải quyết vấn đề rất tốt', 4, '2024-02-15 16:30:00', NOW(), NOW()),
(4, 2, 4, 96.0, 'Chuyên cần xuất sắc', 4, '2024-02-15 16:30:00', NOW(), NOW()),
(4, 2, 6, 95.0, 'Kiến thức mobile xuất sắc', 4, '2024-02-15 16:30:00', NOW(), NOW()),
(4, 2, 7, 97.0, 'Hoàn thành xuất sắc mọi task', 4, '2024-02-15 16:30:00', NOW(), NOW()),

-- Assignment 5 (Student 10 - Hoàng Văn Em) - Round 1
(5, 1, 1, 87.0, 'Kỹ năng Docker và K8s tốt', 5, '2024-01-25 17:00:00', NOW(), NOW()),
(5, 1, 2, 89.0, 'Làm việc nhóm tốt', 5, '2024-01-25 17:00:00', NOW(), NOW()),
(5, 1, 4, 92.0, 'Chuyên cần tốt', 5, '2024-01-25 17:00:00', NOW(), NOW()),
(5, 1, 6, 85.0, 'Kiến thức DevOps cơ bản tốt', 5, '2024-01-25 17:00:00', NOW(), NOW()),
(5, 1, 7, 88.0, 'Hoàn thành tốt task', 5, '2024-01-25 17:00:00', NOW(), NOW()),

-- Assignment 5 - Round 2
(5, 2, 1, 90.0, 'Tiến bộ về CI/CD pipeline', 5, '2024-02-15 17:30:00', NOW(), NOW()),
(5, 2, 2, 91.0, 'Teamwork tốt hơn', 5, '2024-02-15 17:30:00', NOW(), NOW()),
(5, 2, 3, 87.0, 'Giải quyết vấn đề infrastructure tốt', 5, '2024-02-15 17:30:00', NOW(), NOW()),
(5, 2, 4, 93.0, 'Chuyên cần tốt', 5, '2024-02-15 17:30:00', NOW(), NOW()),
(5, 2, 6, 88.0, 'Kiến thức DevOps đã cải thiện', 5, '2024-02-15 17:30:00', NOW(), NOW()),
(5, 2, 7, 90.0, 'Hoàn thành tốt các task phức tạp', 5, '2024-02-15 17:30:00', NOW(), NOW());

-- ============================================
-- SUMMARY
-- ============================================
-- Total records inserted:
-- - users: 10 (1 admin, 4 mentors, 5 students)
-- - students: 5
-- - mentors: 4
-- - internship_phases: 5
-- - evaluation_criteria: 7
-- - assessment_rounds: 6
-- - round_criteria: 35 (mapping rounds to criteria)
-- - internship_assignments: 5
-- - assessment_results: 55 (each student has results for 2 rounds)
-- ============================================
