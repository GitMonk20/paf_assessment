-- Task 1
-- Write SQL statements in this file to create the brewery database and 
-- populate the database with the given SQL files

-- Create the brewery database
CREATE DATABASE brewery;

-- Use the brewery database
USE brewery;

-- Execute the provided SQL files
SOURCE beers.sql;
SOURCE breweries.sql;
SOURCE categories.sql;
SOURCE geocodes.sql;
SOURCE styles.sql;

-- Connect with "mysql -h hostname -u username -p"
-- Run with "source create.sql"