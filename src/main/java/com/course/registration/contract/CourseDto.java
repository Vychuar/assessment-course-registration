package com.course.registration.contract;

import com.course.registration.validation.ValidName;
import jakarta.persistence.*;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ValidName
    private String name;
    private int capacity;
    private int currentEnrollment;
}
