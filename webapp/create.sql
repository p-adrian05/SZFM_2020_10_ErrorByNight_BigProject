    create table forum_categories (
       id bigint not null,
        description varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
   );
 
    
    create table messages (
       id bigint not null,
        messageContent varchar(1000) not null,
        status boolean not null,
        timestamp varchar(255) not null,
        primary key (id)
   );
 
    
    create table posts (
       id bigint not null,
        content varchar(1500),
        parentPostOffset integer,
        timestamp varchar(255) not null,
        parentPost_id bigint,
        topic_id bigint not null,
        user_id bigint not null,
        primary key (id)
   );
 
    
    create table roles (
       id bigint not null,
        role varchar(255),
        primary key (id)
   );
 
    
    create table topics (
       id bigint not null,
        description varchar(255),
        lastActiveTimestamp varchar(255) not null,
        timestamp varchar(255) not null,
        title varchar(255) not null,
        category_id bigint not null,
        founder_id bigint not null,
        primary key (id)
   );
 
    
    create table user_messages (
       receiver_Id bigint not null,
        sender_Id bigint not null,
        message_id bigint not null,
        primary key (message_id)
   );
 
    
    create table UserData (
       userId bigint not null,
        city varchar(255),
        fullName varchar(255),
        profileImg varchar(255),
        publicEmail varchar(255),
        primary key (userId)
   );
 
    
    create table users (
       id bigint not null,
        activation varchar(255) not null,
        created timestamp not null,
        email varchar(255) not null,
        enabled boolean not null,
        lastLogin timestamp,
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
   );
 
    
    create table users_favTopics (
       user_id bigint not null,
        topic_id bigint not null,
        primary key (user_id, topic_id)
   );
 
    
    create table users_role (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
   );

    alter table users
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
 create sequence hibernate_sequence start with 1 increment by 1;
 
    
    alter table posts 
       add constraint FK9486systq0as3niw3bkwxp5ux 
       foreign key (parentPost_id) 
       references posts;
 
    
    alter table posts 
       add constraint FKrfchr8dax0kfngvvkbteh5n7h 
       foreign key (topic_id) 
       references topics;
 

    alter table posts
       add constraint FK5lidm6cqbc7u4xhqpxm898qme
       foreign key (user_id) 
       references users;
 
    
    alter table topics 
       add constraint FK4vd7l6uv73pfq9i8jb8rrape4 
       foreign key (category_id) 
       references forum_categories;
 
    
    alter table topics 
       add constraint FKljg4ado2jjyga8m6focan13lu 
       foreign key (founder_id) 
       references users;
 
    
    alter table user_messages 
       add constraint FK21kn8gq0nye2eix8918lx0g7u 
       foreign key (message_id) 
       references messages;
 
    
    alter table UserData 
       add constraint FK4sein2hef5sqpsbpu6qepqti4 
       foreign key (userId) 
       references users;
 
    
    alter table users_favTopics 
       add constraint FKo05incirebqq7q9txbk0vqtm 
       foreign key (topic_id) 
       references topics;
 
    
    alter table users_favTopics 
       add constraint FKl3ggjbwd496mib6l8sdgg0bun 
       foreign key (user_id) 
       references users;
 
    
    alter table users_role 
       add constraint FKeejqlb4gq1av9540jg66ju2pi 
       foreign key (role_id) 
       references roles;
 
    
    alter table users_role 
       add constraint FKqpe36jsen4rslwfx5i6dj2fy8 
       foreign key (user_id) 
       references users;