package com.indoorshape.treinoemcasa.service

import com.indoorshape.treinoemcasa.client.PagamentoClient
import com.indoorshape.treinoemcasa.model.StatusFicha
import com.indoorshape.treinoemcasa.repository.FichaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class PagamentoService(
    private val pagamentoClient: PagamentoClient,
    private val fichaService: FichaService
) {
    private val logger = LoggerFactory.getLogger(PagamentoService::class.java)

    fun gerarLinkPagamento(fichaId: UUID): String {
        return pagamentoClient.criarPreferencia(fichaId.toString())
    }

    fun processarWebhook(action: String?, paymentId: String?) {
        logger.info("🔄 Recebido webhook - Action: $action, Payment ID: $paymentId")

        if (action?.startsWith("payment.") == true && paymentId != null) {
            try {
                val payment = pagamentoClient.consultarPagamento(paymentId.toLong())
                logger.info("📥 Dados do pagamento recebidos: status=${payment.status}, externalReference=${payment.externalReference}")

                val fichaId = payment.externalReference
                if ((payment.status == "approved" || payment.status == "paid") && fichaId != null) {
                    fichaService.atualizarStatus(UUID.fromString(fichaId), StatusFicha.PAGO)
                    logger.info("✅ Ficha $fichaId atualizada para PAGO com sucesso.")
                } else if (fichaId != null) {
                    logger.info("🟡 Pagamento não aprovado. Status: ${payment.status}. Nenhuma atualização realizada para a ficha $fichaId.")
                } else {
                    logger.warn("⚠️ Pagamento com status ${payment.status}, mas fichaId nulo.")
                }
            } catch (ex: Exception) {
                logger.error("❌ Erro ao processar pagamento no webhook: ${ex.message}", ex)
                throw ex  // Importante para o Controller saber que falhou
            }
        } else {
            logger.warn("⚠️ Webhook ignorado: action inválida ou paymentId nulo")
        }
    }


}
