package com.example.filiere.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    @NotNull(message = "code  is required")
    @Size(min = 3, max = 50, message = "min c 2 and max is 50 c ")
    private String code;

    @NotNull(message = "libelle  is required")
    @Size(min = 3, max = 50, message = "min c 2 and max is 50 c ")
    private String libelle;
}
