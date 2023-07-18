package com.courseregistration.service;

import com.courseregistration.contract.CourseResponse;
import com.courseregistration.exception.CourseNotFoundException;
import com.courseregistration.model.Course;
import com.courseregistration.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        List<CourseResponse> responses = new ArrayList<>();
        for (Course c : courses) {
            responses.add(
                    CourseResponse
                            .builder()
                            .id(c.getId())
                            .name(c.getName())
                            .capacity(c.getCapacity())
                            .currentEnrollment(c.getCurrentEnrollment())
                            .build()
            );
        }
        return responses;
    }


    public CourseResponse getCourseById(Long id) {
        Course course = this.courseRepository.findById(id).orElseThrow(() -> {
            return new CourseNotFoundException(id);
        });
        return CourseResponse
                .builder()
                .id(course.getId())
                .name(course.getName())
                .capacity(course.getCapacity())
                .currentEnrollment(course.getCurrentEnrollment())
                .build();
    }

}
