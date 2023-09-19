package com.example.les11model.controller;

import com.example.les11model.dto.CourseDto;
import com.example.les11model.model.Course;
import com.example.les11model.model.Teacher;
import com.example.les11model.repository.CourseRepository;
import com.example.les11model.repository.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public CourseController(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.title);
        for (Long id : courseDto.teacherIds) {
            Teacher teacher = teacherRepository.findById(id).get();
            course.getTeachers().add(teacher);  // koppeling aan het course object //
        }
        courseRepository.save(course);
        courseDto.id = course.getId();
        return new ResponseEntity<>(courseDto, HttpStatus.CREATED);
    }
}
// here usually is a service layer !!! //

//TODO: FILL IN ALL NECESSARY FIELDS IN DIFFERENT CLASSES //