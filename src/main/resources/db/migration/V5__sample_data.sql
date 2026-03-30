INSERT INTO users (id, name, email, password)
VALUES (1, 'John Doe', 'john@example.com', 'password123'),
       (2, 'Jane Smith', 'jane@example.com', 'password123'),
       (3, 'Alice Johnson', 'alice@example.com', 'password123');

INSERT INTO profiles (id, bio, phone_number, date_of_birth, loyalty_points)
VALUES (1, 'Tech enthusiast and gadget lover', '1234567890', '1990-05-15', 120),
       (2, 'Fitness freak and traveler', '2345678901', '1988-09-22', 250),
       (3, 'Bookworm and coffee addict', '3456789012', '1995-03-10', 75);

INSERT INTO addresses (id, street, city, state, zip, user_id)
VALUES (1, '123 Main St', 'Dallas', 'TX', '75001', 1),
       (2, '456 Oak Ave', 'Irving', 'TX', '75062', 2),
       (3, '789 Pine Rd', 'Plano', 'TX', '75074', 3);

INSERT INTO categories (id, name)
VALUES (1, 'Electronics'),
       (2, 'Books'),
       (3, 'Clothing');

INSERT INTO products (id, name, price, description, category_id)
VALUES (1, 'iPhone 15', 999.99, 'Latest Apple smartphone with advanced features', 1),
       (2, 'Samsung TV', 799.99, '55-inch 4K Ultra HD Smart TV', 1),
       (3, 'Atomic Habits', 19.99, 'Self-help book by James Clear', 2),
       (4, 'Nike T-Shirt', 29.99, 'Comfortable cotton t-shirt', 3),
       (5, 'Laptop Backpack', 49.99, 'Durable backpack for everyday use', 1);

INSERT INTO wishlist (product_id, user_id)
VALUES (1, 1),
       (3, 1),
       (2, 2),
       (4, 2),
       (5, 3),
       (1, 3);