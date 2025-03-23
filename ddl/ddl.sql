create table if not exists fooding.user
(
    id              bigint auto_increment primary key,
    role            enum ('ADMIN', 'USER') null,
    email           varchar(255)           not null,
    nickname        varchar(255)           null,
    mobile          varchar(255)           null,
    password        varchar(255)           null,
    refresh_token   varchar(255)           null,
    login_count     int                    not null,
    last_login_date datetime(6)            null,
    marketing_agree bit default b'0'       not null,
    privacy_agree   bit default b'1'       not null,
    terms_agree     bit default b'1'       not null,
    deleted         bit default b'0'       not null,
    created_at      datetime(6)            null,
    created_by      bigint                 null,
    updated_at      datetime(6)            null,
    updated_by      bigint                 null,
    deleted_by      bigint                 null
);
