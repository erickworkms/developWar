package com.develop.war.developWar.controller.exception

import org.hibernate.ObjectNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UsuarioException : RuntimeException() {
    fun saldoInsuficiente(mensagem: String?) {
        println("Saldo insuficiente")
    }

    @ExceptionHandler(ObjectNotFoundException::class)
    fun objetoInvalido() {
        println("Objeto não encontrado")
    }

    @ExceptionHandler(NullPointerException::class)
    fun objetoNullo(objetoVazio: NullPointerException) {
        println("Objeto está vazio" + objetoVazio.message)
    }

    fun contaInvalida(conta: String) {
        println("Conta $conta não encontrada")
    }

    fun usuarioInvalido(usuario: String) {
        println("Usuário $usuario não encontrado")
    }
}