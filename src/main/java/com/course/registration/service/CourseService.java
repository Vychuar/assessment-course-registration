package com.course.registration.service;
import com.course.registration.contract.CourseDto;
import com.course.registration.exception.CourseNotFoundException;
import com.course.registration.model.Course;
import com.course.registration.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    public List<CourseDto> getAllCourses() {
        List<Course> courses = this.courseRepository.findAll();
        return courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
    }

    public CourseDto getCourseById(Long id) {
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        return modelMapper.map(course, CourseDto.class);
    }

    public CourseDto addCourses(CourseDto courseDto) {
        Course courseEntity = courseRepository.save(modelMapper.map(courseDto, Course.class));
        return modelMapper.map(courseEntity, CourseDto.class);

    }


    public CourseDto updateCourseById(Long id, CourseDto course) {
        Optional<Course> oldData = courseRepository.findById(id);

        if (oldData.isPresent()) {
            Course existingCourse = oldData.get();
            modelMapper.map(course, existingCourse);
            Course updatedCourse = courseRepository.save(existingCourse);
            return modelMapper.map(updatedCourse, CourseDto.class);
        } else {
            throw new CourseNotFoundException(id);
        }
    }
public void deleteById(long id){
        if(!courseRepository.existsById(id)){
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }


}
