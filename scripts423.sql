SELECT student.name, student.age, faculty.name
FROM student
         INNER JOIN faculty on student.faculty_id = faculty.id;


SELECT student.name, student.age
FROM student
         RIGHT JOIN avatar on student.id = avatar.student_id