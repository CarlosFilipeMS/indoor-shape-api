package com.indoorshape.treinoemcasa.controller

import com.indoorshape.treinoemcasa.dto.AtualizarStatusDTO
import com.indoorshape.treinoemcasa.dto.FichaRequestDTO
import com.indoorshape.treinoemcasa.dto.FichaResponseDTO
import com.indoorshape.treinoemcasa.dto.PreferenceIdDTO
import com.indoorshape.treinoemcasa.model.StatusFicha
import com.indoorshape.treinoemcasa.service.FichaService
import com.indoorshape.treinoemcasa.service.PagamentoService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/fichas")
class FichaController(
    private val fichaService: FichaService
) {

    @PostMapping
    fun criarFicha(@RequestBody dto: FichaRequestDTO): ResponseEntity<FichaResponseDTO> {
        val ficha = fichaService.criarFicha(dto)
        return ResponseEntity.ok(ficha)
    }

    @PatchMapping("/{id}/pago")
    fun marcarComoPago(@PathVariable id: UUID): ResponseEntity<FichaResponseDTO> {
        val fichaAtualizada = fichaService.atualizarStatus(id, StatusFicha.PAGO)
        return ResponseEntity.ok(fichaAtualizada)
    }

    @GetMapping("/{id}")
    fun buscarFicha(@PathVariable id: UUID): ResponseEntity<FichaResponseDTO> {
        val ficha = fichaService.buscarFicha(id)
        return ResponseEntity.ok(ficha)
    }


}

