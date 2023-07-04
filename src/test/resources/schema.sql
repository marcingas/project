CREATE TABLE address (
	address_id int NOT NULL,
	code varchar(255) NULL,
	number int NULL,
	street varchar(255) NULL,
	town varchar(255) NULL,
	CONSTRAINT address_pkey PRIMARY KEY (address_id)
);

CREATE TABLE cup (
	cup_id int NOT NULL,
	color varchar(255) NULL,
	price numeric(38, 2) NULL,
	shape varchar(255) NULL,
	CONSTRAINT cup_pkey PRIMARY KEY (cup_id)
);

CREATE TABLE customer (
	customer_id int NOT NULL,
	name varchar(255) NULL,
	surname varchar(255) NULL,
	address_id int NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (customer_id)
);
-- customer foreign keys

ALTER TABLE customer ADD CONSTRAINT FOREIGN KEY (address_id)REFERENCES address(address_id);

CREATE TABLE purchase (
	purchase_id int NOT NULL,
	purchase_cost numeric(38, 2) NULL,
	customer_id int NULL,
	CONSTRAINT purchase_pkey PRIMARY KEY (purchase_id)
);
-- purchase foreign keys

ALTER TABLE purchase ADD CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer(customer_id);

CREATE TABLE purchase_cups (
	purchase_id int NOT NULL,
	cup_id int NOT NULL
);

-- purchase_cups foreign keys

ALTER TABLE purchase_cups ADD CONSTRAINT FOREIGN KEY (cup_id) REFERENCES cup(cup_id);
ALTER TABLE purchase_cups ADD CONSTRAINT FOREIGN KEY (purchase_id) REFERENCES purchase(purchase_id);

