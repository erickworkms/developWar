package com.develop.war.developWar.controller

import com.develop.war.developWar.model.Usuario
import com.develop.war.developWar.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
class UsuarioController {
    @Autowired
    private val usuarioService: UsuarioService? = null
    @PostMapping("/criarLogin")
    fun criarLogin(@RequestBody dadosUsuario: Usuario?): ResponseEntity<String> {
        System.out.println("passsou pelo criar login")
        return usuarioService?.criarLogin(dadosUsuario) ?:
        ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("ocorreu um erro nos dados recebidos")
    }
    @PostMapping("/verlogin")
    fun verLoginUsuario(@RequestParam nome: String?, @RequestParam senha: String?): ResponseEntity<String>? {
        return usuarioService?.verLoginUsuario(nome, senha)
    }

    @PostMapping("/retornarLogin")
    fun retornarDadosLogin(
        @RequestBody dadosUsuario: Usuario?,
        @RequestHeader("Authorization") usuario: String?
    ): ResponseEntity<String> {
        System.out.println("passsou por este login")
        return usuarioService?.retornarDadosLogin(dadosUsuario, usuario) ?:
        ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("ocorreu um erro nos dados recebidos")
    }
}
