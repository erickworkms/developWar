package com.develop.war.developWar.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import java.io.Serial
import java.sql.Types
import java.util.*

@Entity
@Table(name = "DadosJogador")
data class DadosJogador(
    @Id
    @Serial
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_jogador", columnDefinition = "VARCHAR(200)")
    @JdbcTypeCode(Types.VARCHAR)
    private var idJogador: UUID? = null,

    @Column
    private var numeroPartidas: Int = 0,

    @Column
    private var numeroVitorias: Int = 0,

    @Column
    private var numeroDerrotas: Int = 0,

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    val usuario: Usuario? = null
)