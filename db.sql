create database if not exists intern_final_2;
use intern_final_2;


drop table if exists product_category;
drop table if exists category;
drop table if exists product;


create table category (
                          id bigint auto_increment primary key,
                          category_code varchar(10),
                          name nvarchar(255),
                          img nvarchar(2000),
                          created_date datetime,
                          modified_date datetime,
                          created_by nvarchar(255),
                          modified_by nvarchar(255),
                          description nvarchar(1000),
                          status varchar(50)
);
create table product (
                         id bigint auto_increment primary key,
                         name nvarchar(255),
                         img nvarchar(2000),
                         product_code varchar(10),
                         description nvarchar(1000),
                         price double,
                         quantity bigint,
                         status varchar(50),
                         created_date datetime,
                         modified_date datetime,
                         created_by nvarchar(255),
                         modified_by nvarchar(255)
);

create table product_category (
                                  id bigint auto_increment primary key,
                                  product_id bigint,
                                  category_id bigint,
                                  created_date datetime,
                                  modified_date datetime,
                                  created_by nvarchar(255),
                                  modified_by nvarchar(255),
                                  status varchar(10),
                                  constraint fk_productCategory_product foreign key (product_id) references product (id),
                                  constraint fk_productCategory_category foreign key (category_id) references category(id)
);

insert into category (category_code, name, img, created_date, modified_date, created_by, modified_by, description, status)
values
('CTG0000001', N'Giày NIKE', 'anhcategory1', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP', N'Danh mục giày của hãng NIKE', 'ACTIVE'),
('CTG0000002', N'Giày ADIDAS', 'anhcategory2', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP', N'Danh mục giày của hãng ADIDAS', 'ACTIVE');

insert into product(name, img, product_code, description, price, quantity, status, created_date, modified_date, created_by, modified_by)
values
(N'Giày Jordan 1', 'anhsanpham1.jpg', 'SP00000001', N'Giày cổ cao của nike', 10000, 100, 'ACTIVE', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP'),
(N'Giày Dunk Low ', 'anhsanpham2.jpg', 'SP00000002', N'Giày cổ thấp của nike', 10000, 100, 'ACTIVE', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP'),
(N'Giày Dunk Shashiko 1', 'anhsanpham3.jpg', 'SP00000003', N'Giày cổ cao của nike', 10000, 100, 'ACTIVE', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP'),
(N'Giày Ultraboots 4 Adidas', 'anhsanpham4.jpg', 'SP00000004', N'Giày thể thao của ADIDAS', 10000, 100, 'ACTIVE', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP'),
(N'Giày Supper Star Adidas', 'anhsanpham5.jpg', 'SP00000005', N'Giày thời trang của ADIDAS', 10000, 100, 'ACTIVE', '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP');
select * from product;

insert into product_category
values
(1, 1, 1, '2024-09-17 00:00:00', '2024-09-17 00:00:00', 'ADMIN-NTP', 'ADMIN-NTP', 'ACTIVE');

select * from product_category;
