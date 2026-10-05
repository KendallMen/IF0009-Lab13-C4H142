package com.medpharm_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record RecetaRequest(@NotBlank @Size(min = 5) String pacienteNombre,
                            @NotEmpty List<@Valid DetalleRecetaRequest> detalles) { }
