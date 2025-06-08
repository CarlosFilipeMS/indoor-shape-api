package com.indoorshape.treinoemcasa.dto

import com.indoorshape.treinoemcasa.model.StatusFicha
import java.util.UUID

data class FichaResponseDTO (
    val id: UUID?,
    val nome: String,
    val email: String,
    val telefone: String,
)