package com.alberto.portfolio.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Habilidades")
public class Habilidad {

    @Id
    private String id;

    @NotBlank
    @Indexed
    private String name;

    @NotBlank
    private String image;

    @NotBlank
    private String category;
}
