package com.develop.war.developWar.controller.security.token

import com.develop.war.developWar.model.Usuario
import com.develop.war.developWar.repository.UsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.stream.Collectors

@Component
class FiltroToken : OncePerRequestFilter() {
    @Autowired
    var verificaToken: GeradorJWTToken? = null

    @Autowired
    var usuarioRepository: UsuarioRepository? = null
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //esta implementação só esta validando a integridade do token
        //Não esquecer, o token tem que estar completo para o authorization funcionar, logo não tirar o bearer antes do tempo
        val token = recoverToken(request)
        if (token != null && !token.isEmpty()) {
            val login = verificaToken?.verificaToken(token)
            val dadosUsuario: Usuario? = usuarioRepository?.findByUsuario(login)
            //Cria uma variavel que vai armazenar o tipo de acesso como admin user public)
            val authorities : List<SimpleGrantedAuthority>? = dadosUsuario?.let { authorities(it.acesso) }
            //Cria a variavel com o token para autenticação com usuario e senha, credenciais e authorities(admin,user,public etc)
            val tokenUsuario = UsernamePasswordAuthenticationToken(
                dadosUsuario?.usuario ?: "",
                null,
                authorities
            )
            SecurityContextHolder.getContext().authentication = tokenUsuario
        } else {
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(request, response)
    }

    //Cria um vetor com todos os acessos do usuario, por exemplo, admin teria acesso ADMIN e USUARIO
    private fun authorities(roles: List<String>): List<SimpleGrantedAuthority> {
        return roles.stream().map { role: String? ->
            SimpleGrantedAuthority(
                role
            )
        }
            .collect(Collectors.toList())
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization") ?: return null
        return authHeader.replace("Bearer ", "")
    }
}