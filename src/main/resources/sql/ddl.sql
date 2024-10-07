DROP DATABASE wsu;

CREATE DATABASE wsu;

USE wsu;

DROP TABLE IF EXISTS MEMBER;
DROP TABLE IF EXISTS BOARD;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS ALARM;
DROP TABLE IF EXISTS BOARD_LIKE;
DROP TABLE IF EXISTS SUB_COMMENT;
DROP TABLE IF EXISTS NOTICE;

CREATE TABLE member (
    id	BIGINT	NOT NULL	AUTO_INCREMENT PRIMARY KEY,
    login_id	VARCHAR(255)	NOT NULL	COMMENT 'Email 형식',
    password	VARCHAR(255)	NOT NULL,
    name	VARCHAR(255)	NOT NULL,
    age	INT	NOT NULL,
    nationality	VARCHAR(255)	NOT NULL,
    sex	ENUM('FEMALE','MALE') NOT NULL,
    phone	VARCHAR(255)	NOT NULL,
    point	INT	NOT NULL	DEFAULT 0,
    role	ENUM('ROLE_MEMBER', 'ROLE_ADMIN') DEFAULT 'ROLE_MEMBER' NOT NULL COMMENT '회원: MEMBER / 관리자: ADMIN',
    nickname	VARCHAR(255)	NOT NULL,
    profile_url	TEXT	NULL,
    introduction	VARCHAR(255)	NULL,
    language	VARCHAR(255)	NOT NULL,
    created_at	CHAR(19)	NOT NULL,
    updated_at	CHAR(19)	NOT NULL,
    active	BOOLEAN	NOT NULL	DEFAULT TRUE
);

CREATE TABLE board (
    id	BIGINT	NOT NULL	AUTO_INCREMENT PRIMARY KEY,
    title	VARCHAR(255)	NOT NULL,
    content	TEXT	NOT NULL,
    image_url	TEXT	NULL	COMMENT 'AWS S3를 통해서 이미지 업로드 및 저장',
    tag	ENUM('GUIDE', 'FREEMARKET', 'ACCOMPANY', 'TIP') NOT NULL,
    comment_count	INT	NOT NULL	DEFAULT 0,
    likes	INT	NOT NULL	DEFAULT 0,
    created_at	CHAR(19)	NOT NULL,
    update_at	CHAR(19)	NOT NULL,
    active	BOOLEAN	NOT NULL	DEFAULT true,
    member_id	BIGINT	NOT NULL
);

CREATE TABLE product (
     id	BIGINT	NOT NULL	AUTO_INCREMENT PRIMARY KEY,
     title	VARCHAR(255)	NOT NULL,
     content	TEXT	NOT NULL,
     image_url	TEXT	NOT NULL,
     category	ENUM('NECESSITIES', 'CLOTHES', 'KITCHENWARES', 'TOY', 'DEVICE', 'ETC') NOT NULL,
     start_at	CHAR(19)	NOT NULL,
     end_at	CHAR(19)	NOT NULL,
     rental	BOOLEAN	NOT NULL	DEFAULT FALSE	COMMENT '대여되면 TRUE',
     created_at	CHAR(19)	NOT NULL,
     updated_at	CHAR(19)	NOT NULL,
     admin_id	BIGINT	NOT NULL	COMMENT 'ROLE이 ADMIN일 경우에만 가능',
     member_id	BIGINT
);

CREATE TABLE comment (
     id	BIGINT	NOT NULL	AUTO_INCREMENT PRIMARY KEY,
     content	VARCHAR(255)	NOT NULL,
     created_at	CHAR(19)	NOT NULL,
     updated_at	CHAR(19)	NOT NULL,
     member_id	BIGINT	NOT NULL,
     board_id	BIGINT	NOT NULL
);

CREATE TABLE alarm (
    id	BIGINT	NOT NULL	AUTO_INCREMENT PRIMARY KEY,
    message	VARCHAR(255)	NOT NULL,
    read_status	BOOLEAN NOT NULL DEFAULT FALSE	COMMENT '읽으면 TRUE',
    created_at	CHAR(19)	NOT NULL,
    member_id	BIGINT	NOT NULL
);

CREATE TABLE board_like (
    member_id	BIGINT	NOT NULL,
    board_id	BIGINT	NOT NULL
);

CREATE TABLE sub_comment (
     id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
     content	VARCHAR(255)	NOT NULL,
     created_at	CHAR(19)	NOT NULL,
     updated_at	CHAR(19)	NOT NULL,
     member_id	BIGINT	NOT NULL,
     comment_id	BIGINT	NOT NULL
);

CREATE TABLE notice (
    id	BIGINT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title	VARCHAR(255)	NOT NULL,
    content	TEXT	NOT NULL,
    created_at	CHAR(19)	NOT NULL,
    updated_at	CHAR(19)	NOT NULL,
    active	BOOLEAN	NOT NULL	DEFAULT TRUE	COMMENT '삭제  시 FALSE',
    admin_id	BIGINT	NOT NULL
);

ALTER TABLE board_like ADD CONSTRAINT PRIMARY KEY (
    member_id,
    board_id
);
ALTER TABLE board_like ADD CONSTRAINT FOREIGN KEY (
    member_id
)
REFERENCES member (
    id
);

ALTER TABLE board_like ADD CONSTRAINT FOREIGN KEY (
    board_id
)
REFERENCES board (
    id
);

ALTER TABLE board ADD CONSTRAINT FOREIGN KEY (
    member_id
)
REFERENCES member (
    id
);

ALTER TABLE alarm ADD CONSTRAINT FOREIGN KEY (
member_id
)
REFERENCES member (
    id
);

ALTER TABLE notice ADD CONSTRAINT FOREIGN KEY (
admin_id
)
REFERENCES member (
    id
);

ALTER TABLE product ADD CONSTRAINT FOREIGN KEY (
    admin_id
)
REFERENCES member (
    id
);

ALTER TABLE product ADD CONSTRAINT FOREIGN KEY (
    member_id
)
REFERENCES member (
    id
);

ALTER TABLE sub_comment ADD CONSTRAINT FOREIGN KEY (
    member_id
)
REFERENCES member (
    id
);

ALTER TABLE sub_comment ADD CONSTRAINT FOREIGN KEY (
    comment_id
)
REFERENCES comment (
    id
);

ALTER TABLE comment ADD CONSTRAINT FOREIGN KEY (
    member_id
)
REFERENCES member (
    id
);

ALTER TABLE comment ADD CONSTRAINT FOREIGN KEY (
    board_id
)
REFERENCES board (
    id
);