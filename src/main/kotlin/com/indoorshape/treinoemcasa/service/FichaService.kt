package com.indoorshape.treinoemcasa.service

import com.indoorshape.treinoemcasa.dto.FichaRequestDTO
import com.indoorshape.treinoemcasa.model.Ficha
import com.indoorshape.treinoemcasa.repository.FichaRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FichaService (
    @Autowired private val fichaRepository: FichaRepository
) {
    @Transactional
    fun enviarFicha(fichaRequest: FichaRequestDTO): Ficha{
        val ficha = Ficha(
            id = null,
            nome = fichaRequest.nome,
            email = fichaRequest.email,
            telefone = fichaRequest.telefone,
            altura = fichaRequest.altura,
            peso = fichaRequest.peso,
            objetivo = fichaRequest.objetivo,
            nivel = fichaRequest.nivel,
            limitacao = fichaRequest.limitacao
        )
        return fichaRepository.save(ficha)
    }
}
