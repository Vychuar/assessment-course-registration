package com.courseregistration.service;

import com.courseregistration.contract.RegistrationResponse;
import com.courseregistration.exception.RegistrationNotFoundException;
import com.courseregistration.model.Course;
import com.courseregistration.model.Registration;
import com.courseregistration.repository.CourseRepository;
import com.courseregistration.repository.RegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RegistrationService {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationService(CourseRepository courseRepository, RegistrationRepository registrationRepository) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<RegistrationResponse> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        List<RegistrationResponse> responses = new ArrayList<>();
        for (Registration registration : registrations) {
            Course course = courseRepository.findById(registration.getCourseId()).orElseThrow(() -> {
                return new RegistrationNotFoundException(registration.getCourseId());
            });

            responses.add(
                    RegistrationResponse.builder()
                            .id(registration.getId())
                            .courseId(course.getId())
                            .studentId(registration.getStudentId())
                            .build()
            );
        }
        return responses;
    }

    public RegistrationResponse getRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> {
            return new RegistrationNotFoundException(id);
        });

        Course course = courseRepository.findById(registration.getCourseId()).orElseThrow(() -> {
            return new RegistrationNotFoundException(registration.getCourseId());
        });

        return RegistrationResponse.builder()
                .id(registration.getId())
                .courseId(course.getId())
                .studentId(registration.getStudentId())
                .build();
    }

    public RegistrationResponse addRegistration(Registration registration) {
        Registration addedRegistration = registrationRepository.save(registration);

        Course course = courseRepository.findById(registration.getCourseId()).orElseThrow(() -> {
            return new RegistrationNotFoundException(registration.getCourseId());
        });

        return RegistrationResponse.builder()
                .id(addedRegistration.getId())
                .courseId(course.getId())
                .studentId(addedRegistration.getStudentId())
                .build();
    }

    public RegistrationResponse updateRegistrationById(Long id, Registration updatedRegistration) {
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> {
            return new RegistrationNotFoundException(id);
        });

        registration.setStudentId(updatedRegistration.getStudentId());
        registration.setCourseId(updatedRegistration.getCourseId());

        Registration updatedRegistrationEntity = registrationRepository.save(registration);

        Course course = courseRepository.findById(updatedRegistration.getCourseId()).orElseThrow(() -> {
            return new RegistrationNotFoundException(updatedRegistration.getCourseId());
        });

        return RegistrationResponse.builder()
                .id(updatedRegistrationEntity.getId())
                .courseId(course.getId())
                .studentId(updatedRegistrationEntity.getStudentId())
                .build();
    }

    public void deleteRegistrationById(Long id) {
        registrationRepository.deleteById(id);
    }
}