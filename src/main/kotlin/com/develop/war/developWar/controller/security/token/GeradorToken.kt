package com.develop.war.developWar.controller.security.token

import lombok.Data
import java.util.*

@Data
class GeradorToken {
    var usuario: String? = null
    var dataCriacaoToken: Date? = null
    var dataExpiracaoToken: Date? = null
    var regras: List<String>? = null

}