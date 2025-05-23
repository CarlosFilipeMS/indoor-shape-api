package com.indoorshape.treinoemcasa.model

import com.indoorshape.treinoemcasa.dto.FichaResponseDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "ficha")
data class Ficha (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val telefone: String,

    @Column (nullable = false)
    val altura: BigDecimal,

    @Column(nullable = false)
    val peso: BigDecimal,

    @Column(nullable = false)
    val objetivo: String,

    @Column(nullable = false)
    val nivel: String,

    @Column(nullable = true)
    val limitacao: String?
) {
    fun toDTO() = FichaResponseDTO (
        id = this.id,
        nome = this.nome,
        email = this.email,
        telefone = this.telefone
    )
}