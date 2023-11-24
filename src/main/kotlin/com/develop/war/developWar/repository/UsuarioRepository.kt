package com.develop.war.developWar.repository

import com.develop.war.developWar.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario,UUID>{
    @Query("SELECT u FROM Usuario u JOIN FETCH u.acesso acesso WHERE u.usuario = :usuario")
    fun findByUsuario(@Param("usuario") usuario: String?): Usuario?

    @Query("SELECT u.idUsuario FROM Usuario u WHERE u.usuario = :usuario")
    fun findIdByUsuario(@Param("usuario") usuario: String?): String?

    fun existsByUsuario(Usuario: String?): Boolean
}