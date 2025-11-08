package com.example.Student.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

        @NotNull(message = "first name  is required")
        @Size(min = 3, max = 50, message = "min c 2 and max is 50 c ")
        private String fristname;

        @NotNull(message = "last name  is required")
        @Size(min = 3, max = 50, message = "min c 2 and max is 50 c ")
        private String lastname;

        @NotNull(message = "CNE  is required")
        @Size(min = 3, max = 50, message = "min c 2 and max is 50 c ")
        private String CNE;

        @NotNull(message = " filier name  is required")
        private long idFilier;

}
