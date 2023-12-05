create table users
(
    id         bigint not null
        constraint users_id_pk
            primary key,
    type varchar,
    user_name     varchar
        constraint users_user_name_pk
            unique,
    password  varchar,
    email      varchar
        constraint users_email_pk
            unique,
    private_number varchar,
    active     boolean default true
);

alter table users
    owner to leon;

create unique index users_id_uindex
    on users (id);

create sequence "users_id_seq";

alter sequence "users_id_seq" owned by users.id;

alter table users
    alter column id set default nextval('users_id_seq'::regclass);

insert into users(id, type, user_name, password, email) values (-1,'ADMIN','admin','$2a$12$VR.2cC1Jz9VOzBSJdTq1puTXHf9T5MDCL8YFbhYmsTkESprVGqlJm','@crocobet.com');


create table authorities
(
    id        bigint               not null
        constraint authorities_pk
            primary key,
    name      varchar              not null,
    parent_id bigint,
    active    boolean default TRUE not null
);

alter table authorities
    owner to leon;

alter table authorities
    add constraint authorities_authorities_id_fk
        foreign key (parent_id) references authorities;

create unique index if not exists authorities_id_uindex
    on authorities (id);

create sequence "authorities_id_seq";

alter sequence "authorities_id_seq" owned by authorities.id;

alter table authorities
    alter column id set default nextval('authorities_id_seq'::regclass);

create table user_authorities
(
    id           bigint
        constraint user_authorities_pk
            primary key,
    user_id      bigint               not null
        constraint user_authorities_users_id_fk
            references users,
    authority_id bigint               not null
        constraint user_authorities_authorities_id_fk
            references authorities,
    active       boolean default true not null
);

alter table user_authorities
    owner to leon;


create sequence "user_authorities_id_seq";

alter sequence "user_authorities_id_seq" owned by user_authorities.id;

alter table user_authorities
    alter column id set default nextval('user_authorities_id_seq'::regclass);