package com.develop.war.developWar.model

import jakarta.persistence.*
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.JdbcTypeCode
import java.io.Serial
import java.io.Serializable
import java.sql.Types
import java.util.*

@NoArgsConstructor // cria construtor vazio

@Data
@Entity
@Table(name = "Usuario")
class Usuario(
    @Id
    @Serial
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario", columnDefinition = "VARCHAR(200)")
    @JdbcTypeCode(Types.VARCHAR)
    private var idUsuario: UUID? = null,

    @Column(length = 100, nullable = false)
    var nome: String = "",

    @Column(length = 20, nullable = false)
    var dataNascimento: String = "",

    @Column(length = 50, nullable = false)
    var email: String = "",

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tab_usuario_regras", joinColumns = [JoinColumn(name = "id_usuario")])
    @Column(name = "acesso_id")
    var acesso: MutableList<String> = mutableListOf(),

    @Column(length = 50, nullable = false)
    var usuario: String = "",

    @Column(length = 200, nullable = false)
    var senha: String = "",

    @OneToMany(mappedBy = "usuario", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jogador: List<DadosJogador> = mutableListOf()
) : Serializable{
    constructor(
        nome: String,
        dataNascimento: String,
        email: String,
        acesso: MutableList<String>,
        usuario: String,
        senha: String,
        jogador: List<DadosJogador>
    ) : this(null, nome, dataNascimento, email, acesso, usuario, senha, jogador)
}