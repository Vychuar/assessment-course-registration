package com.courseregistration.contract;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationResponse {
        private Long id;
        private Long courseId;
        private Long studentId;
}
