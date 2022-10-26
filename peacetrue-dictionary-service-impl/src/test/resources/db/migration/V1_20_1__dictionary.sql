-- 初始化数据 @formatter:off
insert into dictionary_type (id, code, name, creator_id, created_time, modifier_id, modified_time) values (1, 'sex', '性别', 0, '2022-10-25 00:00:00', 0, '2022-10-25 00:00:00');
insert into dictionary_value (id, dictionary_type_id, dictionary_type_code, code, name, serial_number, creator_id,created_time, modifier_id, modified_time) values(1,1,'sex','man','男',1,0,'2022-10-25 00:00:00',0,'2022-10-25 00:00:00');
