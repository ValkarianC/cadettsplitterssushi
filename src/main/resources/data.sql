
INSERT INTO sushi_dish (dish_name, dish_price) VALUES
                                                   ('Sashimi', 169),
                                                   ('Salmon Sushi', 129),
                                                   ('Vegetarian Sushi', 129),
                                                   ('California Maki', 89),
                                                   ('Dragon Maki', 169),
                                                   ('Rainbow Maki', 149),
                                                   ('Yakitori', 119),
                                                   ('Tempura', 159);

INSERT INTO sushi_room (room_name, room_capacity, room_is_available) VALUES
                                                                         ('Härnö Sushi', 50, true),
                                                                         ('Sundsvall Sushi Bar', 75, true),
                                                                         ('New China Restaurant', 150, true),
                                                                         ('Wagamamas', 25, false),
                                                                         ('McSushi', 12, true);

INSERT INTO sushi_booking (booking_customer, booking_guest_number, room_id, booking_date, booking_time, booking_total_price, booking_is_cancelled) VALUES
                                                                                                                                                       ('benjaminportsmouth', 10, 1, '2025-10-10', '17:00:00', 169, false),
                                                                                                                                                       ('benjaminportsmouth', 10, 1, '2025-10-08', '17:00:00', 169, true),
                                                                                                                                                       ('niklaseinarsson', 10, 1, '2025-10-08', '17:00:00', 169, true),
                                                                                                                                                       ('cristofferfrisk', 10, 1, '2025-10-08', '17:00:00', 169, true);


