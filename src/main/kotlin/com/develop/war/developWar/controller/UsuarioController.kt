package com.develop.war.developWar.controller

import com.develop.war.developWar.model.DadosRegistroSala
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
        System.out.println("passou pelo criar login")
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
    @PostMapping("/salvarSala")
    fun salvarSalaLobby(
        @RequestBody dadosSala: DadosRegistroSala?,
        @RequestHeader("Authorization") usuario: String?
    ): ResponseEntity<String> {
        System.out.println("passsou por este login")
        println(dadosSala)
        println(usuario)
        return usuarioService?.salvarDadosSala(dadosSala, usuario) ?:
        ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("ocorreu um erro nos dados recebidos")
    }

    @GetMapping("/verDadosSalas")
    fun verDadosSalaLobby(@RequestHeader("Authorization") usuario: String?): ResponseEntity<List<DadosRegistroSala>>? {
        return usuarioService?.verDadosSalaLobby(usuario)
    }
    @DeleteMapping("/deletaSalasLobby")
    fun deletaSalaLobby(@RequestHeader("Authorization") usuario: String?,@RequestBody usuarioCriador: String?): ResponseEntity<String>? {
        return usuarioService?.deletaSalaLobby(usuario,usuarioCriador)
    }
}
