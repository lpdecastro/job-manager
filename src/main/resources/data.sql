insert into roles (name, description)
values ('CANDIDATE', 'Default role for candidates');

insert into roles (name, description)
values ('RECRUITER', 'Default role for recruiters');

insert into roles (name, description)
values ('ADMIN', 'Default role for admins');

insert into users (username, email, password, created_at, updated_at, role_id)
values ('candidate', 'candidate@yopmail.com', '$2a$12$HWYu2HlFYZgKlDFUoxMMh.vEgH0MWaMx0jensucx1hfWt4bEGBMru',
        current_timestamp, current_timestamp, 1);

insert into users (username, email, password, created_at, updated_at, role_id)
values ('recruiter', 'recruiter@yopmail.com', '$2a$12$HWYu2HlFYZgKlDFUoxMMh.vEgH0MWaMx0jensucx1hfWt4bEGBMru',
        current_timestamp, current_timestamp, 2);

insert into users (username, email, password, created_at, updated_at, role_id)
values ('admin', 'admin@yopmail.com', '$2a$12$HWYu2HlFYZgKlDFUoxMMh.vEgH0MWaMx0jensucx1hfWt4bEGBMru',
        current_timestamp, current_timestamp, 3);

insert into candidates (user_id)
values (1);

insert into recruiters (user_id)
values (2);

insert into companies (name)
values ('name');

insert into  job_locations (city, province, zip_code, country, company_id)
values ('city1', 'province', '1111', 'PH', 1);
insert into  job_locations (city, province, zip_code, country, company_id)
values ('city2', 'province', '1111', 'PH', 1);
insert into  job_locations (city, province, zip_code, country, company_id)
values ('city3', 'province', '1111', 'PH', 1);

insert into job_listings (title, description, type, remote, salary, created_at, updated_at, company_id, job_location_id)
values ('title1', 'description', 'FULL_TIME', 'REMOTE', 80000.0, current_timestamp, current_timestamp, 1, 1);
insert into job_listings (title, description, type, remote, salary, created_at, updated_at, company_id, job_location_id)
values ('title1', 'description', 'PART_TIME', 'ONSITE', 80000.0, current_timestamp, current_timestamp, 1, 2);
insert into job_listings (title, description, type, remote, salary, created_at, updated_at, company_id, job_location_id)
values ('title1', 'description', 'CONTRACT', 'REMOTE', 80000.0, current_timestamp, current_timestamp, 1, 3);
insert into job_listings (title, description, type, remote, salary, created_at, updated_at, company_id, job_location_id)
values ('title2', 'description', 'FULL_TIME', 'ONSITE', 80000.0, current_timestamp, current_timestamp, 1, 1);
insert into job_listings (title, description, type, remote, salary, created_at, updated_at, company_id, job_location_id)
values ('title2', 'description', 'PART_TIME', 'REMOTE', 80000.0, current_timestamp, current_timestamp, 1, 2);
insert into job_listings (title, description, type, remote, salary, created_at, updated_at, company_id, job_location_id)
values ('title2', 'description', 'CONTRACT', 'ONSITE', 80000.0, current_timestamp, current_timestamp, 1, 3);