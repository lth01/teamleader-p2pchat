create table chatting_room(
    id bigint primary key auto_increment,
    room_name varchar(255) not null,
    maximum int4 not null,
    current_peers int4 not null,
    created_at timestamp,
    updated_at timestamp
);

create table peer_info (
    id bigint primary key auto_increment,
    nickname varchar(255) not null,
    ip varchar(255) not null,
    port int4 not null,
    current_chatting_room_id bigint not null,
    created_at timestamp,
    updated_at timestamp
--    foreign key (current_chatting_room_id) references chatting_room(id)
);