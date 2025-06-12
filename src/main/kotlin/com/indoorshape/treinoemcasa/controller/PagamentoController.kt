package com.indoorshape.treinoemcasa.controller

import com.indoorshape.treinoemcasa.dto.PreferenceIdDTO
import com.indoorshape.treinoemcasa.service.PagamentoService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/pagamento")
class PagamentoController(
    private val pagamentoService: PagamentoService,
    @Value("\${webhook.secret}") private val webhookSecret: String
) {
    private val logger = LoggerFactory.getLogger(PagamentoController::class.java)

    @PostMapping("/criar-preferencia/{fichaId}")
    fun criarPreferencia(@PathVariable fichaId: UUID): ResponseEntity<PreferenceIdDTO> {
        return try {
            val initPoint = pagamentoService.gerarLinkPagamento(fichaId)
            ResponseEntity.ok(PreferenceIdDTO(initPoint))
        } catch (ex: Exception) {
            logger.error("Erro ao criar preferência: ${ex.message}")
            ResponseEntity.internalServerError().build()
        }
    }

    @PostMapping("/webhook")
    fun receberWebhook(
        @RequestParam("token", required = false) token: String?,
        @RequestBody payload: Map<String, Any>
    ): ResponseEntity<String> {

        if (token == null) {
            logger.warn("⚠️ Webhook recebido sem token")
            return ResponseEntity.status(403).body("Token ausente")
        }

        if (token != webhookSecret) {
            logger.warn("❌ Tentativa de webhook com token inválido")
            return ResponseEntity.status(403).body("Forbidden")
        }

        logger.info("🔔 Webhook recebido: $payload")

        val action = payload["action"]?.toString()
        val data = payload["data"] as? Map<*, *>
        val paymentId = data?.get("id")?.toString()

        if (action != null && paymentId != null) {
            try {
                pagamentoService.processarWebhook(action, paymentId)
                logger.info("✅ Webhook processado com sucesso para paymentId=$paymentId")
                return ResponseEntity.ok("✅ Webhook processado com sucesso.")
            } catch (ex: Exception) {
                logger.error("❌ Erro ao processar webhook: ${ex.message}", ex)
                return ResponseEntity.internalServerError().body("Erro ao processar webhook")
            }
        }

        logger.warn("⚠️ Webhook recebido com payload inválido: faltando action ou ID de pagamento")
        return ResponseEntity.badRequest().body("Faltando action ou ID de pagamento.")
    }


    @GetMapping("/retorno")
    fun retornoPagamento(
        @RequestParam("payment_id") paymentId: String?,
        @RequestParam("status") status: String?
    ): ResponseEntity<String> {
        if (paymentId.isNullOrBlank()) {
            logger.warn("⚠️ Payment ID não informado na URL de retorno")
            return ResponseEntity.badRequest().body("Payment ID ausente")
        }

        try {
            pagamentoService.processarWebhook("payment.updated", paymentId)
            logger.info("🔁 Pagamento $paymentId processado com sucesso via retorno")
            return ResponseEntity.ok("Pagamento confirmado com sucesso.")
        } catch (ex: Exception) {
            logger.error("❌ Erro ao processar retorno do pagamento: ${ex.message}", ex)
            return ResponseEntity.internalServerError().body("Erro ao processar pagamento")
        }
    }


}
