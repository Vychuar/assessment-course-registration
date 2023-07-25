package com.course.registration.contract;

import com.course.registration.validation.ValidCapacity;
import com.course.registration.validation.ValidCurrentEnrollment;
import com.course.registration.validation.ValidName;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDto {

    private Long id;
    @ValidName private String name;
    @ValidCapacity private int capacity;
    @ValidCurrentEnrollment private int currentEnrollment;
}
