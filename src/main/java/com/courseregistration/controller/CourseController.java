package com.courseregistration.controller;

import com.courseregistration.service.CourseService;
import com.courseregistration.contract.CourseResponse;
import com.courseregistration.model.Course;
import com.courseregistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping
@RestController
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }


    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<CourseResponse> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        CourseResponse course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course addedCourse = courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCourse);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourseById(@PathVariable Long id, @RequestBody Course newCourseData) {
        Optional<Course> oldCourseData = courseRepository.findById(id);
        if (oldCourseData.isPresent()) {
            Course updatedCourse = oldCourseData.get();
            updatedCourse.setName(newCourseData.getName());
            updatedCourse.setCapacity(newCourseData.getCapacity());
            updatedCourse.setCurrentEnrollment(newCourseData.getCurrentEnrollment());

            Course savedCourse = courseRepository.save(updatedCourse);
            return new ResponseEntity<>(savedCourse, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
