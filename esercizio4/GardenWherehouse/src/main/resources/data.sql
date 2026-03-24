delete from Item;
INSERT INTO Item (id, code, type, name, price, item_Count)
VALUES
    (next value for item_seq, 'abc-12', 'ELECTRONICS', 'Mouse Wireless', 19.99, 50),
    (next value for item_seq, 'def-34', 'ELECTRONICS', 'Keyboard Mechanical', 79.90, 30),
    (next value for item_seq, 'ghi-56', 'BOOK', 'Clean Code', 34.50, 20),
    (next value for item_seq, 'jkl-78', 'FOOD', 'Protein Bar', 2.99, 200),
    (next value for item_seq, 'mno-90', 'CLOTHING', 'T-Shirt Cotton', 14.99, 100),
    (next value for item_seq, 'pqr-11', 'ELECTRONICS', 'USB-C Cable', 9.99, 150),
    (next value for item_seq, 'stu-22', 'BOOK', 'Design Patterns', 49.00, 0),
    (next value for item_seq, 'vwx-33', 'FOOD', 'Coffee Beans', 12.75, 80),
    (next value for item_seq, 'yza-44', 'CLOTHING', 'Jeans Slim Fit', 59.99, 40),
    (next value for item_seq, 'bcd-55', 'ELECTRONICS', 'Webcam HD', 39.90, 25);

