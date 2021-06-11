package br.com.forum.controller.dto

import br.com.forum.model.Topico
import java.time.LocalDateTime

data class TopicoDto(val id: Long?, val titulo: String, val mensagem: String, val dataCriacao: LocalDateTime?){

    constructor(topico: Topico) : this(topico.id, topico.titulo, topico.mensagem, topico.dataCriacao)

    companion object {
        fun converter(topicos: List<Topico>): List<TopicoDto>{
            val result = arrayListOf<TopicoDto>()
            topicos.forEach(){
                result.add(TopicoDto(id = it.id, titulo = it.titulo, mensagem = it.mensagem, dataCriacao = it.dataCriacao))
            }
            return result
        }
    }
}