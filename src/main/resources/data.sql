INSERT INTO products(product_id, product_name, product_code, description, release_date, price, currency, star_rating, image_url, create_date_time, update_date_time)
VALUES
  ('9cfae4f0-e5fc-4d91-be83-3656a2776931', 'Basic Model', 'EXP1', 'sample products', '2016-08-25', 10.99, 'USD', '5', 'http://example.com', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  ('56fb94e8-7e85-48a3-8f59-866ad16bdefa', 'Standard Model', 'EXP2', 'sample std products', '2017-08-25', 15.49, 'USD', '5', 'http://example.com', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());