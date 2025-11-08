package com.example.Student.DTO;

import jakarta.persistence.Column;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private Long id ;

    private String fristname;

    private String lastname;

    private String CNE;

    private FiliereDto filiereDto;
}
