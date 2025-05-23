package com.indoorshape.treinoemcasa.dto

import java.math.BigDecimal
import jakarta.validation.constraints.*

data class FichaRequestDTO(
    @field:NotBlank val nome: String,
    @field:Email val email: String,
    @field:NotBlank val telefone: String,
    @field:DecimalMin("0.0") val altura: BigDecimal,
    @field:DecimalMin("0.0") val peso: BigDecimal,
    @field:NotBlank val objetivo: String,
    @field:NotBlank val nivel: String,
    val limitacao: String?
)
