INSERT INTO gi_comuni_cap 
SELECT * FROM CSVREAD('classpath:comuni.csv', null, 'fieldSeparator=;');