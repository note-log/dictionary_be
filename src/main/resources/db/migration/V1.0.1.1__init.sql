ALTER TABLE user ADD COLUMN name VARCHAR(10) not null comment '姓名';
ALTER TABLE user MODIFY COLUMN username VARCHAR(128) unique not null COMMENT '学号';
CREATE INDEX idx_name ON user(name);
CREATE INDEX idx_phone ON user(phone);