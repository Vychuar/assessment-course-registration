package com.course.registration.controller;

import com.course.registration.contract.CourseDto;
import com.course.registration.repository.CourseRepository;
import com.course.registration.service.CourseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        CourseDto course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<CourseDto> addCourses(@Valid @RequestBody CourseDto course) {
        CourseDto courseDto = courseService.addCourses(course);
        return new ResponseEntity<>(courseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourseById(
            @PathVariable Long id, @Valid @RequestBody CourseDto course) {
        CourseDto updatedCourses = courseService.updateCourseById(id, course);
        return ResponseEntity.ok(updatedCourses);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable Long id) {

        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
