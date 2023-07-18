package com.courseregistration.contract;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
public class CourseResponse {
    private Long id;
    private String name;
    private int capacity;
    private int currentEnrollment;

}
