package com.indoorshape.treinoemcasa.service

import com.indoorshape.treinoemcasa.dto.FichaRequestDTO
import com.indoorshape.treinoemcasa.dto.FichaResponseDTO
import com.indoorshape.treinoemcasa.model.Ficha
import com.indoorshape.treinoemcasa.model.StatusFicha
import com.indoorshape.treinoemcasa.repository.FichaRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FichaService(
    private val fichaRepository: FichaRepository
) {

    fun criarFicha(dto: FichaRequestDTO): FichaResponseDTO {
        val ficha = Ficha(
            nome = dto.nome,
            email = dto.email,
            telefone = dto.telefone,
            altura = dto.altura,
            peso = dto.peso,
            objetivo = dto.objetivo,
            nivel = dto.nivel,
            limitacao = dto.limitacao,
            status = StatusFicha.PENDENTE

        )
        fichaRepository.save(ficha)
        return ficha.toResponseDTO()
    }

    fun buscarFicha(id: UUID): FichaResponseDTO {
        val ficha = fichaRepository.findById(id)
            .orElseThrow { RuntimeException("Ficha não encontrada") }
        return ficha.toResponseDTO()
    }


    fun atualizarStatus(id: UUID, novoStatus: StatusFicha): FichaResponseDTO {
        val ficha = fichaRepository.findById(id)
            .orElseThrow { RuntimeException("Ficha não encontrada") }

        ficha.status = novoStatus  // Usa o status recebido na requisição
        fichaRepository.save(ficha)
        return ficha.toResponseDTO()
    }


    // Extensão para converter entidade para DTO
    private fun Ficha.toResponseDTO() = FichaResponseDTO(
        id = this.id,
        nome = this.nome,
        email = this.email,
        telefone = this.telefone,
    )
}


