package com.alberto.portfolio.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Proyecto")
public class Proyecto {

    @Id
    private String id;

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 100, message = "Nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "Descripción es requerida")
    @Size(max = 500, message = "Descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotNull(message = "Tecnologías es requerido")
    @Size(min = 1, max = 3, message = "Debe tener entre 1 y 3 tecnologías")
    private List<
            @NotBlank(message = "Cada tecnología no puede estar en blanco")
            @Size(max = 50, message = "Cada tecnología no puede exceder 50 caracteres")
                    String> tecnologias;

    @NotBlank(message = "Enlace es requerido")
    @Size(max = 200, message = "Enlace no puede exceder 200 caracteres")
    private String enlace;

    public Proyecto(String nombre, String descripcion, List<String> tecnologias, String enlace) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologias = tecnologias;
        this.enlace = enlace;
    }
}
