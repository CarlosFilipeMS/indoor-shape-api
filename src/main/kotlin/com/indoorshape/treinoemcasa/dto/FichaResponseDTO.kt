package com.indoorshape.treinoemcasa.dto

import java.util.UUID

data class FichaResponseDTO (
    val id: UUID?,
    val nome: String,
    val email: String,
    val telefone: String
)
