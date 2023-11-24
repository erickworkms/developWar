package com.develop.war.developWar.repository

import com.develop.war.developWar.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DadosJogadorRepository : JpaRepository<Usuario,UUID>{
}