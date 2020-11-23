 insert into users (activation, created, email, enabled, lastLogin, password, username,id) values
        ('12er', '2020-11-08', 'adrian@gmail.com', true, '2020-11-08', '1234', 'adrian',22);

 insert into users (activation, created, email, enabled, lastLogin, password, username,id) values
('12e234', '2020-11-08', 'adrian2@gmail.com', true, '2020-11-08', '123432', 'adrian2',23);
insert into userdata (userid, city,fullname,profileimg,publicemail) values (22,'Debrecen','fname','def','public');
insert into userdata (userid, city,fullname,profileimg,publicemail) values (23,'Debrecen','fname','def','public');
insert into roles (role, id)
    values
        ('USER', 1);

insert into users_role (user_id, role_id) values (22, 1);
insert into users_role (user_id, role_id) values (23, 1);
insert into messages (id, messageContent,status, timestamp ) values (1,'Hello',true,'2020-11-08');
insert into messages (id, messageContent,status, timestamp ) values (2,'Hello hello',false,'2020-11-08');
insert into user_messages (receiver_id, sender_id,message_id) values (22, 23,1);
insert into user_messages (receiver_id, sender_id,message_id) values (23, 22,2);

insert into forum_categories (id,description,title) values (1, 'test description','testCategory');
insert into topics (id,description,timestamp,title,category_id,founder_id,lastactivetimestamp)
 values (1, 'testTopic description','2020-11-10','testTopic',1,22,'2020-11-11');
insert into topics (id,description,timestamp,title,category_id,founder_id,lastactivetimestamp)
values (2, 'testTopic2 description','2020-11-10','testTopic2',1,22,'2020-11-11');

insert into posts (id,content,timestamp ,parentpost_id,topic_id,user_id,parentpostoffset)
values (1, 'test post','2020-11-11',null,1,22,0);
insert into posts (id,content,timestamp ,parentpost_id,topic_id,user_id,parentpostoffset)
values (2, 'test post2','2020-11-11',null,2,22,0);
insert into posts (id,content,timestamp ,parentpost_id,topic_id,user_id,parentpostoffset)
values (3, 'test post3','2020-11-11',null,2,22,0);

insert into users_favtopics (user_id,topic_id) values(22,1);
insert into users_favtopics (user_id,topic_id) values(22,2);



