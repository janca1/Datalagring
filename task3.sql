CREATE VIEW lessons_per_month4 AS SELECT COUNT(*) AS total, DATE_FORMAT(date, '%m') AS month from lesson WHERE DATE_FORMAT(date, '%Y')='2022' GROUP BY DATE_FORMAT(date, '%m');

CREATE VIEW lessons_per_month6 AS SELECT COUNT(*) AS total, DATE_FORMAT(date, '%m') AS month, lesson_type AS type from lesson WHERE DATE_FORMAT(date, '%Y')='2022' GROUP BY DATE_FORMAT(date, '%m'), lesson_type;

CREATE VIEW average_per_year4 AS SELECT COUNT(*) AS total_lessons, COUNT(*)/12 AS average_lessons_per_moth, DATE_FORMAT(date, '%Y') AS year from lesson WHERE DATE_FORMAT(date, '%Y')='2022';

CREATE VIEW average_per_year5 AS SELECT COUNT(*) AS total_lessons, COUNT(*)/12 AS average_lessons_per_month, lesson_type AS type from lesson WHERE DATE_FORMAT(date, '%Y')='2022' GROUP BY lesson_type;

CREATE VIEW instructors5 AS SELECT * from (SELECT COUNT(*) AS number_of_lessons_this_month, instructor_id AS instructor_id 
FROM lesson WHERE DATE_FORMAT(date,'%Y, %m')=DATE_FORMAT(CURDATE(),'%Y, %m') 
GROUP BY instructor_id ORDER BY number_of_lessons_this_month) AS instructors WHERE number_of_lessons_this_month > 7;

CREATE VIEW ensembles2 AS SELECT *,
CASE
    WHEN maximum_number_of_students - number_of_students = 0 THEN "Full booked"
    WHEN maximum_number_of_students - number_of_students > 0 AND maximum_number_of_students - number_of_students < 3 THEN "1-2 seats left"
    WHEN maximum_number_of_students - number_of_students > 2 THEN "More than 2 seats left"
END AS seats_left
FROM lesson WHERE lesson_type='ensemble' AND DATE_FORMAT(date, "%U") = WEEK(CURDATE())+1 AND DATE_FORMAT(date, "%Y") = DATE_FORMAT(CURDATE(), '%Y')
ORDER BY genre, dayofweek(date);