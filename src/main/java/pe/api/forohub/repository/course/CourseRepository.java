package pe.api.forohub.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.api.forohub.models.course.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
