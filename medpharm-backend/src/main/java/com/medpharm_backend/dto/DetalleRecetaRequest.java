package com.medpharm_backend.dto;

import jakarta.validation.constraints.*;

public record DetalleRecetaRequest(@NotNull Long medicamentoId, @NotNull @Positive Integer cantidad,
                                   @NotBlank String dosisIndicada) { }
