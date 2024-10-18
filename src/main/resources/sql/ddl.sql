SET foreign_key_checks = 0;

DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS BOARD_COMMENT;
DROP TABLE IF EXISTS BOARD_RE_COMMENT;
DROP TABLE IF EXISTS ALARM;
DROP TABLE IF EXISTS NOTICE;


-- 테이블 삭제 순서
DROP TABLE IF EXISTS BOARD_LIKE;
DROP TABLE IF EXISTS NOTICE;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS BOARD;
DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS BOARD_IMAGE;

-- 테이블 생성
CREATE TABLE member (
                        member_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        member_login_id VARCHAR(255) NOT NULL COMMENT 'Email 형식',
                        member_password VARCHAR(255) NOT NULL,
                        member_name VARCHAR(255) NOT NULL,
                        member_age INT NOT NULL,
                        member_nationality VARCHAR(255) NOT NULL,
                        member_sex ENUM('FEMALE','MALE') NOT NULL,
                        member_phone VARCHAR(255) NOT NULL,
                        member_point INT NOT NULL DEFAULT 0,
                        member_role ENUM('ROLE_MEMBER', 'ROLE_ADMIN') DEFAULT 'ROLE_MEMBER' NOT NULL COMMENT '회원: MEMBER / 관리자: ADMIN',
                        member_nickname VARCHAR(255) NOT NULL,
                        member_profile_url TEXT NULL,
                        member_introduction VARCHAR(255) NULL,
                        member_language VARCHAR(255) NOT NULL,
                        member_created_at TIMESTAMP NOT NULL,
                        member_updated_at TIMESTAMP NOT NULL,
                        member_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE board (
                       board_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       board_title VARCHAR(255) NOT NULL,
                       board_content TEXT NOT NULL,
#                        board_image_url TEXT NULL COMMENT 'AWS S3를 통해서 이미지 업로드 및 저장',
                       board_tag ENUM('GUIDE', 'FREEMARKET', 'ACCOMPANY', 'TIP') NOT NULL,
                       board_comment_count INT NOT NULL DEFAULT 0,
                       board_likes_count INT NOT NULL DEFAULT 0,
                       board_created_at CHAR(19) NOT NULL,
                       board_updated_at CHAR(19) NOT NULL,
                       board_active BOOLEAN NOT NULL DEFAULT true,
                       member_id BIGINT NOT NULL
);

CREATE TABLE board_image (
                             board_image_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             image_url TEXT NOT NULL COMMENT 'S3에 저장된 이미지 URL',
                             board_id BIGINT NOT NULL
);


CREATE TABLE product (
                         product_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         product_title VARCHAR(255) NOT NULL,
                         product_content TEXT NOT NULL,
                         product_image_url TEXT NOT NULL,
                         product_category ENUM('NECESSITIES', 'CLOTHES', 'KITCHENWARES', 'TOY', 'DEVICE', 'ETC') NOT NULL,
                         product_start_at CHAR(19) NOT NULL,
                         product_end_at CHAR(19) NOT NULL,
                         product_rental BOOLEAN NOT NULL DEFAULT FALSE COMMENT '대여되면 TRUE',
                         product_created_at CHAR(19) NOT NULL,
                         product_updated_at CHAR(19) NOT NULL,
                         admin_id BIGINT NOT NULL COMMENT 'ROLE이 ADMIN일 경우에만 가능',
                         member_id BIGINT
);

CREATE TABLE board_comment (
                               board_comment_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               board_comment_content VARCHAR(255) NOT NULL,
                               board_comment_created_at CHAR(19) NOT NULL,
                               board_comment_updated_at CHAR(19) NOT NULL,
                               member_id BIGINT NOT NULL,
                               board_id BIGINT NOT NULL
);

CREATE TABLE alarm (
                       alarm_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       alarm_message VARCHAR(255) NOT NULL,
                       alarm_read_status BOOLEAN NOT NULL DEFAULT FALSE COMMENT '읽으면 TRUE',
                       alarm_created_at CHAR(19) NOT NULL,
                       member_id BIGINT NOT NULL
);

CREATE TABLE board_like (
                            member_id BIGINT NOT NULL,
                            board_id BIGINT NOT NULL,
                            PRIMARY KEY (member_id, board_id)
);

CREATE TABLE board_re_comment (
                                  board_re_comment_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  board_re_comment_content VARCHAR(255) NOT NULL,
                                  board_re_comment_created_at CHAR(19) NOT NULL,
                                  board_re_comment_updated_at CHAR(19) NOT NULL,
                                  member_id BIGINT NOT NULL,
                                  comment_id BIGINT NOT NULL
);

CREATE TABLE notice (
                        notice_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        notice_title VARCHAR(255) NOT NULL,
                        notice_content TEXT NOT NULL,
                        notice_created_at CHAR(19) NOT NULL,
                        notice_updated_at CHAR(19) NOT NULL,
                        notice_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT '삭제 시 FALSE',
                        admin_id BIGINT NOT NULL
);

-- 제약 조건 추가
ALTER TABLE board_like ADD CONSTRAINT fk_board_like_member FOREIGN KEY (member_id) REFERENCES member (member_id);
ALTER TABLE board_like ADD CONSTRAINT fk_board_like_board FOREIGN KEY (board_id) REFERENCES board (board_id);

ALTER TABLE board ADD CONSTRAINT fk_board_member FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE alarm ADD CONSTRAINT fk_alarm_member FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE notice ADD CONSTRAINT fk_notice_admin FOREIGN KEY (admin_id) REFERENCES member (member_id);

ALTER TABLE product ADD CONSTRAINT fk_product_admin FOREIGN KEY (admin_id) REFERENCES member (member_id);
ALTER TABLE product ADD CONSTRAINT fk_product_member FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE board_re_comment ADD CONSTRAINT fk_re_comment_member FOREIGN KEY (member_id) REFERENCES member (member_id);
ALTER TABLE board_re_comment ADD CONSTRAINT fk_re_comment_comment FOREIGN KEY (board_re_comment_id) REFERENCES board_comment (board_comment_id);

ALTER TABLE board_comment ADD CONSTRAINT fk_comment_member FOREIGN KEY (member_id) REFERENCES member (member_id);
ALTER TABLE board_comment ADD CONSTRAINT fk_comment_board FOREIGN KEY (board_id) REFERENCES board (board_id);

ALTER TABLE board_image ADD CONSTRAINT  fk_board_image_board FOREIGN KEY (board_id) REFERENCES board (board_id);

INSERT INTO member (
    member_active, member_age, member_point, member_created_at, member_id,
    member_updated_at, member_introduction, member_language, member_login_id,
    member_name, member_nationality, member_nickname, member_password,
    member_phone, member_profile_url, member_role, member_sex
) VALUES
      (true, 21, 0, '2024-10-17 21:06:18.897189', 1, '2024-10-17 21:11:36.905416', '',
       'KOREAN', 'aa@gmail.com', '도시번', 'seoul', '가지남',
       '{bcrypt}$2a$10$czC26QxE66BEEymt1rlNCuAWfs5YfVAUTCDNhlwJbF.IpBprnfhBi',
       '01012345678',
       'https://weshareubk.s3.ap-northeast-2.amazonaws.com/2d84d5ce-a84a-46b0-884a-cca21548f902.png',
       'ROLE_MEMBER', 'FEMALE'),

      (true, 29, 0, '2024-10-17 21:07:48.313106', 2, '2024-10-17 21:12:08.585902', '',
       'JAPANESE', 'bb@gmail.com', '조시엄', 'tokyo', '조시나',
       '{bcrypt}$2a$10$qStVmE9cukq2UhCPg7rVp.RiMIB4x8/g9u8GDqravV4OgfncTb71a',
       '01029403390',
       'https://weshareubk.s3.ap-northeast-2.amazonaws.com/c10a0719-602d-4776-b645-bc4e60abce4e.jpeg',
       'ROLE_MEMBER', 'FEMALE'),

      (true, 31, 0, '2024-10-17 21:09:03.547522', 3, '2024-10-17 21:12:44.089542', '',
       'ENGLISH', 'cc@gmail.com', '김기호', 'newyork', '사리꾼',
       '{bcrypt}$2a$10$yPLn2XgJt51D8U3Y8Q87uemsVo9O/gYpDVt5BxOS89oe/Tj26Dd12',
       '01049310025',
       'https://weshareubk.s3.ap-northeast-2.amazonaws.com/14a0a4b8-db81-4eb2-b585-3bdb69726d64.png',
       'ROLE_MEMBER', 'MALE'),

      (true, 33, 0, '2024-10-17 21:10:46.973141', 4, '2024-10-17 21:10:46.973141', NULL,
       'CHINESE', 'dd@gmail.com', '김만삭', 'chengdu', '사리꾼',
       '{bcrypt}$2a$10$LIaRqX2ozjlTym7dPxeMquGxh9VVnr/x4sWXzJ9pdsF.uCuf4QN4y',
       '01039663344', NULL, 'ROLE_MEMBER', 'MALE');

# INSERT INTO board (board_title, board_content, board_image_url, board_tag, board_comment_count, board_likes_count, board_created_at, board_updated_at, board_active, member_id) VALUES
INSERT INTO board (board_title, board_content, board_tag, board_comment_count, board_likes_count, board_created_at, board_updated_at, board_active, member_id) VALUES
                                                                                                                                                                   ('Guide to Paris', 'A detailed guide to traveling in Paris.', 'GUIDE', 0, 0, '2024-10-08T12:00:00', '2024-10-08T12:00:00', TRUE, 1),
                                                                                                                                                                   ('Selling Camping Gear', 'Selling my used camping gear at a good price.', 'FREEMARKET', 0, 0, '2024-10-08T12:00:00', '2024-10-08T12:00:00', TRUE, 2),
                                                                                                                                                                   ('Looking for Travel Companion', 'Looking for a companion for a trip to Spain.', 'ACCOMPANY', 0, 0, '2024-10-08T12:00:00', '2024-10-08T12:00:00', TRUE, 3);

INSERT INTO product (product_title, product_content, product_image_url, product_category, product_start_at, product_end_at, product_rental, product_created_at, product_updated_at, admin_id, member_id) VALUES
                                                                                                                                                                                                             ('Tent', 'A two-person tent for camping.', 'tent_image_url', 'NECESSITIES', '2024-10-01T00:00:00', '2024-11-01T00:00:00', FALSE, '2024-10-08T12:00:00', '2024-10-08T12:00:00', 1, 2),
                                                                                                                                                                                                             ('Winter Jacket', 'A warm winter jacket.', 'jacket_image_url', 'CLOTHES', '2024-10-01T00:00:00', '2024-11-01T00:00:00', FALSE, '2024-10-08T12:00:00', '2024-10-08T12:00:00', 1, 3),
                                                                                                                                                                                                             ('Cooking Pot', 'A durable cooking pot for camping.', 'pot_image_url', 'KITCHENWARES', '2024-10-01T00:00:00', '2024-11-01T00:00:00', FALSE, '2024-10-08T12:00:00', '2024-10-08T12:00:00', 1, 3);

INSERT INTO board_comment (board_comment_content, board_comment_created_at, board_comment_updated_at, member_id, board_id) VALUES
                                                                                                                               ('This is a great guide, thanks for sharing!', '2024-10-08T12:00:00', '2024-10-08T12:00:00', 1, 1),
                                                                                                                               ('I am interested in the camping gear. Please contact me.', '2024-10-08T12:00:00', '2024-10-08T12:00:00', 2, 2),
                                                                                                                               ('I would love to join you for the Spain trip!', '2024-10-08T12:00:00', '2024-10-08T12:00:00', 3, 3);

INSERT INTO alarm (alarm_message, alarm_read_status, alarm_created_at, member_id) VALUES
                                                                                      ('You have a new comment on your post.', FALSE, '2024-10-08T12:00:00', 1),
                                                                                      ('Your item has been rented.', FALSE, '2024-10-08T12:00:00', 2),
                                                                                      ('You have a new follower.', FALSE, '2024-10-08T12:00:00', 3);

INSERT INTO board_re_comment (board_re_comment_content, board_re_comment_created_at, board_re_comment_updated_at, member_id, comment_id) VALUES
                                                                                                                                             ('Thank you for the information!', '2024-10-08T12:00:00', '2024-10-08T12:00:00', 2, 1),
                                                                                                                                             ('Please let me know the price.', '2024-10-08T12:00:00', '2024-10-08T12:00:00', 3, 2),
                                                                                                                                             ('I will DM you for more details.', '2024-10-08T12:00:00', '2024-10-08T12:00:00', 1, 3);

INSERT INTO notice (notice_title, notice_content, notice_created_at, notice_updated_at, notice_active, admin_id) VALUES
                                                                                                                     ('Service Maintenance', 'The service will be down for maintenance on October 10th.', '2024-10-08T12:00:00', '2024-10-08T12:00:00', TRUE, 1),
                                                                                                                     ('New Features Released', 'We have added new features to improve your experience.', '2024-10-08T12:00:00', '2024-10-08T12:00:00', TRUE, 1),
                                                                                                                     ('Community Guidelines Update', 'Please review the updated community guidelines.', '2024-10-08T12:00:00', '2024-10-08T12:00:00', TRUE, 1);

INSERT INTO board_image (image_url, board_id) VALUES
                                                  ('https://s3.amazonaws.com/bucket-name/image1.jpg', 1),
                                                  ('https://s3.amazonaws.com/bucket-name/image2.jpg', 1),
                                                  ('https://s3.amazonaws.com/bucket-name/image3.jpg', 2);
