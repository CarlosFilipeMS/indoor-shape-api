package com.indoorshape.treinoemcasa.controller

import com.indoorshape.treinoemcasa.dto.FichaRequestDTO
import com.indoorshape.treinoemcasa.dto.FichaResponseDTO
import com.indoorshape.treinoemcasa.model.Ficha
import com.indoorshape.treinoemcasa.service.FichaService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:5173"])
@RestController
@RequestMapping("/fichas")
class FichaController (
    @Autowired private val fichaService: FichaService
) {
    @PostMapping
    fun enviarFicha(@RequestBody @Valid fichaRequest: FichaRequestDTO): ResponseEntity<FichaResponseDTO> {
        val novaFicha = fichaService.enviarFicha(fichaRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFicha.toDTO())
    }
}