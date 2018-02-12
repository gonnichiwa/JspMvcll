
desc board;
desc reply;

drop table board;
drop table reply;

create table board(
  num int primary key,
  name varchar2(20) not null,
  password varchar2(20) not null,
  subject varchar2(20) not null,
  content varchar2(2000) not null,
  write_date varchar2(20) not null,
  write_time varchar2(20) not null,
  ref int not null,
  step int not null,
  lev int not null,
  read_cnt int not null,
  child_cnt int not null
);

create table reply(
  rpy_num int primary key,
  rpy_author varchar2(50) not null,
  rpy_content varchar2(400) not null,
  rpy_date varchar2(70) not null,
  rpy_parent_num int not null
);

create sequence seq_rpy_num
start with 1
increment by 1
nomaxvalue
nocache;
