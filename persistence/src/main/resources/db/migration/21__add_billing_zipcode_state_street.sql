ALTER TABLE payments
ADD COLUMN zipcode varchar(10),
ADD COLUMN state varchar(256),
ADD COLUMN country varchar(256);