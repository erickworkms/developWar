package com.develop.war.developWar.controller.security.token

import com.develop.war.developWar.model.Usuario
import com.develop.war.developWar.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.HashSet

@Configuration
class VerSegurancaServicoBD : UserDetailsService{

    @Autowired
    private val usuarioRepository: UsuarioRepository? = null
    override fun loadUserByUsername(usuario: String?): UserDetails {
        val usuarioDados: Usuario = usuarioRepository?.findByUsuario(usuario) ?: throw UsernameNotFoundException(usuario)
        val previlegio: MutableSet<GrantedAuthority> = HashSet()
        usuarioDados.acesso.forEach { acesso -> previlegio.add(SimpleGrantedAuthority("USUARIO_$acesso")) }
        return User(
            usuarioDados.usuario,
            usuarioDados.senha,
            previlegio
        )
    }
}