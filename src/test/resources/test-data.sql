INSERT INTO customer (customer_id, name, surname)
VALUES (1,'John','Doe'),
       (2,'Filip','Hajzer');

INSERT INTO cup (cup_id,color, shape, price)
VALUES (1,'Red','Circle',9.99),
       (2,'Blue','Square',12.99),
       (3,'Orange','Square',22.99),
       (4,'Green','Square',16.99),
       (5,'Rusty','Square',15.99),
       (6,'Yellow','curly',8.00);

INSERT INTO purchase (purchase_id,customer_id,purchase_cost)
VALUES (1,1,32.12),
       (2,1,25.45),
       (3,2,8.00);

INSERT INTO purchase_cups(purchase_id, cup_id)
VALUES (1,1),
       (1,2),
       (1,3),
       (2,4),
       (2,5),
       (3,6);


