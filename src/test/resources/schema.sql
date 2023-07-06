CREATE TABLE address (
	address_id int NOT NULL,
	code varchar(255),
	number int,
	street varchar(255),
	town varchar(255),
	CONSTRAINT address_pkey PRIMARY KEY (address_id)
);

CREATE TABLE cup (
	cup_id int NOT NULL,
	color varchar(255),
	price numeric(38, 2),
	shape varchar(255),
	CONSTRAINT cup_pkey PRIMARY KEY (cup_id)
);

CREATE TABLE customer (
	customer_id int NOT NULL,
	name varchar(255),
	surname varchar(255),
	address_id int,
	CONSTRAINT customer_pkey PRIMARY KEY (customer_id)
);
-- customer foreign keys

--ALTER TABLE customer ADD CONSTRAINT FOREIGN KEY (address_id)REFERENCES address(address_id);

CREATE TABLE purchase (
	purchase_id int NOT NULL,
	purchase_cost numeric(38, 2),
	customer_id int,
	CONSTRAINT purchase_pkey PRIMARY KEY (purchase_id)
);
-- purchase foreign keys

--ALTER TABLE purchase ADD CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

CREATE TABLE purchase_cups (
	purchase_id int NOT NULL,
	cup_id int NOT NULL
);


-- purchase_cups foreign keys

--ALTER TABLE purchase_cups ADD CONSTRAINT FOREIGN KEY (cup_id) REFERENCES cup(cup_id);
--ALTER TABLE purchase_cups ADD CONSTRAINT FOREIGN KEY (purchase_id) REFERENCES purchase(purchase_id);