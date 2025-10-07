create table if not exists security.sec_privilege (id uuid not null, name varchar(500) not null unique, primary key (id));
create table if not exists security.sec_role (id uuid not null, name varchar(500) not null, primary key (id));
create table if not exists security.sec_roles_privileges (privilege_id uuid not null, role_id uuid not null, unique (role_id, privilege_id),constraint FKcn0qpj0j6cls2s8sr9vtiw5yk foreign key (privilege_id) references security.sec_privilege,constraint FKmajukqfpkpqa6wpsyrirya2yt foreign key (role_id) references security.sec_role);
create table if not exists security.sec_user (account_not_expired boolean not null, account_not_locked boolean not null, credentials_not_expired boolean not null, enabled boolean not null, id uuid not null, password varchar(500), user_code varchar(500) not null, user_name varchar(500) not null, primary key (id));
create table if not exists security.sec_users_privileges (privilege_id uuid not null, user_id uuid not null, unique (user_id, privilege_id),constraint FKt9m3sqjsjeb8ijhkeqkwg1846 foreign key (privilege_id) references security.sec_privilege,constraint FKt6vg4i45k4jbwqcfxf8lagmsl foreign key (user_id) references security.sec_user);
create table if not exists security.sec_users_roles (role_id uuid not null, user_id uuid not null, unique (user_id, role_id),constraint FK9aii3pwrp563ptso2qm52rpb2 foreign key (role_id) references security.sec_role,constraint FK98flom9q9nfivdy2b7828psrh foreign key (user_id) references security.sec_user);

CREATE TABLE if not exists SECURITY.SEC_LOG_OPERATION (
	ID BINARY(16) NOT NULL,
	DATE_TIME_OPERATION TIMESTAMP,
	NEW_VALUE CLOB,
	OLD_VALUE CLOB,
	OPERATAION TINYINT,
	"TYPE" VARCHAR(500),
	USER_CODE VARCHAR(500) NOT NULL,
	USER_NAME VARCHAR(500) NOT NULL,
	VALUE_ID BINARY(16),
	VERSION BIGINT DEFAULT 1 NOT NULL,
	CONSTRAINT SYS_PK_10160 PRIMARY KEY (ID)
);