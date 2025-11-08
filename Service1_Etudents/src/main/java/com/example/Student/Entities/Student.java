package com.example.Student.Entities;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "first_Name", nullable = false, length = 100)
    private String fristname;
    @Column(name = "Last_Name", nullable = false, length = 100)
    private String lastname;
    @Column(name = "CNE", nullable = false, length = 100)
    private String CNE;
    @Column(name = "IDFilier", nullable = false, length = 100)
    private Long idFilier;
}
