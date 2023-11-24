package com.develop.war.developWar.service

import com.develop.war.developWar.controller.security.token.GeradorJWTToken
import com.develop.war.developWar.controller.security.token.GeradorToken
import com.develop.war.developWar.model.Usuario
import com.develop.war.developWar.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService {

    @Autowired
    private val usuarioRepository: UsuarioRepository? = null


    @Value("\${security.config.expiration}")
    private val configSegurancaToken: Long? = null

    @Autowired
    private val codificador: PasswordEncoder? = null

    @Autowired
    var verificaToken: GeradorJWTToken? = null

    fun criarLogin(dadosUsuario: Usuario?): ResponseEntity<String> {

        var usuario = usuarioRepository?.findByUsuario(dadosUsuario?.usuario)
        println("passou antes de tudo")
        if (usuario == null && dadosUsuario != null) {
            println("passou pelo primeiro")
                val novoUsuario = Usuario(
                    dadosUsuario.nome,
                    dadosUsuario.dataNascimento,
                    dadosUsuario.email,
                    ArrayList(),
                    dadosUsuario.usuario,
                    codificador?.encode(dadosUsuario.senha) ?: dadosUsuario.senha,
                    ArrayList()
                )
                println("passou pelo segundo")

                novoUsuario.acesso.add("USUARIO")
                usuarioRepository?.save(novoUsuario)
                println(novoUsuario.toString())
                println("passou pelo final")

                return ResponseEntity.status(HttpStatus.OK).body("Usuario foi registrado com sucesso")
            }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe no sistema")
        }
    }

    fun retornarDadosLogin(dadosUsuario: Usuario?, usuario: String?): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(dadosUsuario.toString())
    }

    fun verLoginUsuario(nome: String?, senha: String?): ResponseEntity<String> {
        val autenticacao = SecurityContextHolder.getContext().authentication

        if (autenticacao != null && autenticacao.isAuthenticated && autenticacao.principal !== "anonymousUser") {
            return ResponseEntity.status(HttpStatus.OK).body("usuario ja esta autenticado " + autenticacao.credentials)
        } else if (autenticacao == null) {
            val usuario: Usuario? = usuarioRepository?.findByUsuario(nome)
            System.out.println(usuario.toString())

            if (usuario != null) {
                println(usuario.usuario)
            }
            if (usuario != null) {
                println(usuario.senha)
            }
            if (usuario != null) {
                if (!usuario.usuario.isEmpty() && !usuario.senha.isEmpty()) {
                    val passwordOk: Boolean = codificador?.matches(senha, usuario.senha) ?: false

                    if (passwordOk) {
                        //Estamos enviando um objeto Sessão para retornar mais informações do usuário
                        var clienteToken = GeradorToken()
                        clienteToken.dataCriacaoToken = Date(System.currentTimeMillis())
                        clienteToken.dataExpiracaoToken = Date(System.currentTimeMillis() + configSegurancaToken!!)
                        clienteToken.regras = usuario.acesso
                        clienteToken.usuario = usuario.usuario
                        return ResponseEntity.status(HttpStatus.OK).body(verificaToken?.criaToken(clienteToken))
                    } else {
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha inválida para o login: $nome")
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao tentar fazer login, verifique seu usuário e senha")
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao tentar fazer login, verifique seu usuário e senha")
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Erro ao tentar fazer login, verifique seu usuário e senha")
    }
}