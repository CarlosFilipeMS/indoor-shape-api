package com.indoorshape.treinoemcasa.model

import com.indoorshape.treinoemcasa.dto.FichaResponseDTO
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

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

    @Column(nullable = false)
    val altura: BigDecimal,

    @Column(nullable = false)
    val peso: BigDecimal,

    @Column(nullable = false)
    val objetivo: String,

    @Column(nullable = false)
    val nivel: String,

    @Column(nullable = true)
    val limitacao: String?,

    @Enumerated(EnumType.STRING)
    var status: StatusFicha = StatusFicha.PENDENTE
) {
    fun toDTO() = FichaResponseDTO(
        id = this.id,
        nome = this.nome,
        email = this.email,
        telefone = this.telefone,
    )
}
