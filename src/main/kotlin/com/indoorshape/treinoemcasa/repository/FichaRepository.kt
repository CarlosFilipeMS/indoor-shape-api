package com.indoorshape.treinoemcasa.repository

import com.indoorshape.treinoemcasa.model.Ficha
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FichaRepository: JpaRepository<Ficha, UUID> {
}