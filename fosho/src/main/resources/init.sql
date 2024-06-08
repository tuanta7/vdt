INSERT INTO `users`(role, id, avatar_public_id, avatar_url, email, full_name, password) VALUES
(0,1,NULL,NULL,'bibabibo1989@gmail.com','Phùng Thanh Độ','$2a$10$Fesbavoj6lStmGCp2J936u27LRG2lGDRr2H1iJkVRX7xk8C5QRJ3S'),
(0,2,NULL,NULL,'anhtuan9702@gmail.com','Trần Anh Tuấn','$2a$10$0y2DyWwt9eUXmwkeV2WwXujdwVWGE.L6N0GG2NtC.e17HUAq75coW');

INSERT INTO `restaurants`(is_active, rating,id,owner_id, address, close_time, logo_public_id, logo_url, name, open_time, phone, coordinates) VALUES
(1,4.6,1,1,'86 Phố Nguyễn Văn Tuyết, Trung Liệt, Đống Đa, Hà Nội','22:00','nshtm12fteimdxgicson','http://res.cloudinary.com/foshovdt24/image/upload/v1717780598/nshtm12fteimdxgicson.jpg','Cơm Tấm Sà Bì Chưởng Hà Nội','08:00','0388886666',POINT(105.8206346, 21.0092876)),
(1,0,2,1,'Số 25, Ngõ 38 Yên Lãng, Đống Đa, Hà Nội','22:30','ilkk5083k4pmucxou77f','http://res.cloudinary.com/foshovdt24/image/upload/v1717780729/ilkk5083k4pmucxou77f.png','MixiFood Hà Nội','09:30','0988888992',POINT(105.8139606, 21.0138)),
(1,0,3,2,'AEON Mall, Đường Cổ Linh, Quận Long Bien, Thành phố Hà Nội','22:00','k13mfws77laxbzg60cgz','http://res.cloudinary.com/foshovdt24/image/upload/v1717780997/k13mfws77laxbzg60cgz.jpg','Starbucks Cổ Linh','09:00','0388591079',Point(105.8972768, 21.0268209));

INSERT INTO `dishes` (discount, price, rating, sold, stock, id, restaurant_id, description, name, thumbnail_public_id, thumbnail_url, unit) VALUES
(2000,100000,0,0,200,1,1,'Combo Bestseller của Sà Bì Chưởng đã có mặt trên Fosho!!','Cơm Sườn Cốt Lết','e5r4vpkoohsecvjuzobt','http://res.cloudinary.com/foshovdt24/image/upload/v1717780606/e5r4vpkoohsecvjuzobt.jpg','Suất'),
(15000,200000,0,0,200,2,2,'','Khô Gà Lá Chanh','ucqgcdt6tqdpiq1dpxsh','http://res.cloudinary.com/foshovdt24/image/upload/v1717780760/ucqgcdt6tqdpiq1dpxsh.jpg','Hộp'),
(6000,250000,0,0,200,3,2,'','Ghẹ Sữa Rim','ll13ugo1x9o6stvmo8sf','http://res.cloudinary.com/foshovdt24/image/upload/v1717780783/ll13ugo1x9o6stvmo8sf.jpg','Hộp'),
(0,189000,0,0,200,4,2,'','Bò Cay Xé Sợi','lqymcdecz4en0u69dbba','http://res.cloudinary.com/foshovdt24/image/upload/v1717780808/lqymcdecz4en0u69dbba.jpg','Hộp'),
(11000,200000,0,0,200,5,2,'','Bò Khô Viên','zeqyzmbgh1eufgvtpzwi','http://res.cloudinary.com/foshovdt24/image/upload/v1717780846/zeqyzmbgh1eufgvtpzwi.jpg','Hộp'),
(1000,100000,0,0,250,6,3,'','Grande Caramel Nonfat Latte','onhvjwmct4c13pm24zbd','http://res.cloudinary.com/foshovdt24/image/upload/v1717781119/onhvjwmct4c13pm24zbd.jpg','Cốc (450ml)');

INSERT INTO `order_item`(quantity, dish_id, id, order_id, user_id) VALUES
(1,1,1,NULL,2),
(1,3,2,NULL,2),
(1,4,3,NULL,2);