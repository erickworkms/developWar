package com.develop.war.developWar.model

import jakarta.persistence.*
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.JdbcTypeCode
import java.io.Serial
import java.io.Serializable
import java.sql.Types
import java.util.*

@NoArgsConstructor
@Data
@Entity
@Table(name = "DadosSalas")
class DadosRegistroSala(
    @Id
    @Column(name = "id_Sala", columnDefinition = "VARCHAR(200)")
    @JdbcTypeCode(Types.VARCHAR)
    var idSala: String? = null,

    @Column
    var nomeSala: String? = null,

    @Column
    var mapaIndex: String? = null,

    @Column
    var cenario: String? = null,

    @Column
    var ip: String? = null,

    @Column
    var lan: String? = null,

    @Column
    var numeroJogadoresAtivos: String? = null,

    @Column
    var numeroJogadoresTotais: String? = null,

    @Column
    var servidorDedicado: String? = null

) : Serializable