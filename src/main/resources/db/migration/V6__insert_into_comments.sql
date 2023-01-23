INSERT INTO COMMENTS (date, text, user_id, parent_id, news_id)
VALUES
    (CURRENT_TIMESTAMP, 'This first comment', 2, null, 4),
    (CURRENT_TIMESTAMP, 'Hello', 1, 1, 4),
    (CURRENT_TIMESTAMP, 'Bye!', 3, 2, 4),
    (CURRENT_TIMESTAMP, 'Okay.', 2, null, 5),
    (CURRENT_TIMESTAMP, 'Wow', 3, 3, 5);