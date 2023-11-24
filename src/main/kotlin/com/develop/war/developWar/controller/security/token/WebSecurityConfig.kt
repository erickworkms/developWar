package com.develop.war.developWar.controller.security.token

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Autowired
    var filtroToken: FiltroToken? = null
    @Bean
    fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filtroSeguranca(http: HttpSecurity): SecurityFilterChain {
        return http.cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .anonymous { obj: AnonymousConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests {
                configAutorizacao ->
                configAutorizacao.requestMatchers("/public").permitAll()
                    .requestMatchers(HttpMethod.GET, "/*", "/src/ui/build/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/swagger-ui/index.html").permitAll()
                    .requestMatchers("/sessao", "/sessao/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/usuario/**", "/logout").permitAll()
                    .requestMatchers(*SWAGGER_WHITELIST).authenticated().requestMatchers(HttpMethod.POST, "/*")
                    .hasAnyRole("USUARIO")
                    .anyRequest().permitAll()
            }
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout.deleteCookies("remove")
                    .invalidateHttpSession(false)
                    .logoutUrl("/custom-logout")
                    .logoutSuccessUrl("/logout-success")
            }.sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement
                    .sessionConcurrency {
                        sessionConcurrency ->sessionConcurrency.maximumSessions(1).expiredUrl("/login?expired")
                    }.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(filtroToken, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    companion object {
        private val SWAGGER_WHITELIST = arrayOf(
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui-custom.html"
        )
    }
}
