package com.course.registration.service;

import com.course.registration.contract.RegistrationDto;
import com.course.registration.exception.CourseNotFoundException;
import com.course.registration.exception.RegistrationNotFoundException;
import com.course.registration.model.Registration;
import com.course.registration.repository.CourseRepository;
import com.course.registration.repository.RegistrationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationService {
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RegistrationService(
            CourseRepository courseRepository,
            RegistrationRepository registrationRepository,
            ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
    }

    public List<RegistrationDto> getAllRegistrations() {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations.stream()
                .map(registration -> modelMapper.map(registration, RegistrationDto.class))
                .collect(Collectors.toList());
    }

    public RegistrationDto getRegistrationById(Long id) {
        Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() -> new RegistrationNotFoundException(id));
        return modelMapper.map(registration, RegistrationDto.class);
    }

    public RegistrationDto addRegistration(RegistrationDto registration) {
        Long courseId = registration.getCourseId();
        Long studentId = registration.getStudentId();
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }

        Registration addedRegistration =
                registrationRepository.save(modelMapper.map(registration, Registration.class));
        return modelMapper.map(addedRegistration, RegistrationDto.class);
    }

    public RegistrationDto updateRegistrationById(Long id, RegistrationDto updatedRegistration) {

        if (!courseRepository.existsById(updatedRegistration.getCourseId())) {
            throw new CourseNotFoundException(updatedRegistration.getCourseId());
        }
        Registration registration =
                registrationRepository
                        .findById(id)
                        .orElseThrow(() -> new RegistrationNotFoundException(id));
        Registration updatedRegistrationEntity =
                new Registration(
                        id, updatedRegistration.getCourseId(), updatedRegistration.getStudentId());
        Registration savedRegistrationEntity =
                registrationRepository.save(updatedRegistrationEntity);
        return modelMapper.map(savedRegistrationEntity, RegistrationDto.class);
    }

    public void deleteRegistrationById(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new RegistrationNotFoundException(id);
        }
        registrationRepository.deleteById(id);
    }
}
