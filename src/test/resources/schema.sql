CREATE TABLE address (
	address_id int NOT NULL PRIMARY KEY,
	code varchar(255),
	number int,
	street varchar(255),
	town varchar(255)
);

CREATE TABLE cup (
    cup_id int NOT NULL PRIMARY KEY,
	color varchar(255),
	price numeric(38, 2),
	shape varchar(255)
);

CREATE TABLE customer (
	customer_id int NOT NULL PRIMARY KEY,
	name varchar(255),
	surname varchar(255),
	address_id int,
	FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE purchase (
    purchase_id int NOT NULL PRIMARY KEY,
	purchase_cost numeric(38, 2),
	customer_id int,
	FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);


CREATE TABLE purchase_cups (
	    purchase_id int NOT NULL,
    	cup_id int NOT NULL,
    	PRIMARY KEY (purchase_id, cup_id),
    	FOREIGN KEY (purchase_id)
    	REFERENCES purchase (purchase_id),
    	FOREIGN KEY (cup_id)
    	REFERENCES cup (cup_id)
);



