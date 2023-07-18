package com.courseregistration.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Registrations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId; // This should be a foreign key to the Course table making use of mapping annotation
    private Long studentId;
}
