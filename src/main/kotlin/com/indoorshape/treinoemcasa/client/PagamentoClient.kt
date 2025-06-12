package com.indoorshape.treinoemcasa.client

import com.mercadopago.MercadoPagoConfig
import com.mercadopago.client.payment.PaymentClient
import com.mercadopago.client.preference.*
import com.mercadopago.resources.payment.Payment
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PagamentoClient(
    @Value("\${mercadopago.token}") private val accessToken: String,
    @Value("\${notification.url}") private val notificationUrl: String
) {

    init {
        MercadoPagoConfig.setAccessToken(accessToken)
    }

    fun criarPreferencia(fichaId: String): String {
        val item = PreferenceItemRequest.builder()
            .title("Treino")
            .description("Acesso único a um treino")
            .quantity(1)
            .currencyId("BRL")
            .unitPrice(BigDecimal("0.10"))
            .build()

        val backUrls = PreferenceBackUrlsRequest.builder()
            .success("https://indoor-shape.vercel.app/pagamento/sucesso?fichaId=$fichaId")
            .failure("https://indoor-shape.vercel.app/pagamento/falha")
            .build()

        val request = PreferenceRequest.builder()
            .items(listOf(item))
            .backUrls(backUrls)
            .autoReturn("approved")
            .externalReference(fichaId)
            .notificationUrl(notificationUrl)
            .build()

        val client = PreferenceClient()
        val preference = client.create(request)

        return preference.initPoint
    }

    fun consultarPagamento(paymentId: Long): Payment {
        val paymentClient = PaymentClient()
        return paymentClient.get(paymentId)
    }
}
