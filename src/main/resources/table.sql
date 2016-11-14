DROP SCHEMA IF EXISTS ranceworks;
CREATE SCHEMA ranceworks;

CREATE TABLE ranceworks.access_log(
  id INT AUTO_INCREMENT,
  date_gmt DATE,
  time_gmt TIME,
  city VARCHAR(64),
  country_code VARCHAR(3),
  status_code VARCHAR(3),
  uri VARCHAR(255),
  remote_addr VARCHAR(39),
  http_referer varchar(512),
  http_user_agent VARCHAR(255),
  store_time TIMESTAMP,
  PRIMARY KEY (id)) ;

INSERT INTO ranceworks.access_log(
  date_gmt, 
  time_gmt, 
  city, 
  country_code, 
  status_code, 
  uri, 
  remote_addr, 
  http_referer, 
  http_user_agent,
  store_time) VALUES 
('2016-11-23', '20:23:22', 'Tokyo','JPN', '200','/index.html', '45.24.103.152','-','Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', now());