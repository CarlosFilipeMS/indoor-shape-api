package com.indoorshape.treinoemcasa.config

import com.mercadopago.MercadoPagoConfig
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MercadoPagoInitializer(
    @Value("\${mercadopago.token}") private val accessToken: String
) {
    @PostConstruct
    fun init() {
        MercadoPagoConfig.setAccessToken(accessToken)
    }
}
