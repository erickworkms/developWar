package com.develop.war.developWar.repository

import com.develop.war.developWar.model.DadosRegistroSala
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DadosRegistroSalaRepository : JpaRepository<DadosRegistroSala, UUID> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DadosRegistroSala d WHERE d.usuarioCriador = :usuarioCriador")
    fun deleteAllByUsuarioCriador(@Param("usuarioCriador") usuarioCriador: String?): Int
}