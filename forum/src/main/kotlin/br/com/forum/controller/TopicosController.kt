package br.com.forum.controller

import br.com.forum.controller.dto.DetalhesDoTopicoDto
import br.com.forum.controller.dto.TopicoDto
import br.com.forum.controller.form.AtualizacaoTopicoForm
import br.com.forum.controller.form.TopicoForm
import br.com.forum.model.Topico
import br.com.forum.repository.CursoRepository
import br.com.forum.repository.TopicoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.transaction.Transactional
import javax.validation.Valid


@RestController
@RequestMapping("/topicos")
class TopicosController {

    @Autowired
    lateinit var topicoRepository: TopicoRepository

    @Autowired
    lateinit var cursoRepository: CursoRepository

    @GetMapping
    fun lista(nomeCurso: String?): List<TopicoDto> {
        nomeCurso?.let {
            return TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso))
        }
        return TopicoDto.converter(topicoRepository.findAll())
    }

    @Transactional
    @PostMapping
    fun cadastrar(@RequestBody @Valid topicoForm: TopicoForm, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<TopicoDto>{
        var topico = topicoForm.converter(cursoRepository)
        topico = topicoRepository.save(topico)
        val uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.id).toUri()
        return ResponseEntity.created(uri).body(TopicoDto(topico))
    }

    @GetMapping("/{id}")
    fun detalhar(@PathVariable id: Long): ResponseEntity<DetalhesDoTopicoDto> {
        val topico: Optional<Topico>  = topicoRepository.findById(id)
        if(topico.isPresent){
            return ResponseEntity.ok(DetalhesDoTopicoDto(topico.get()))
        }
        return ResponseEntity.notFound().build()
    }

    @Transactional
    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid atualizacaoTopicoForm: AtualizacaoTopicoForm): ResponseEntity<TopicoDto>{
        val topico: Optional<Topico>  = topicoRepository.findById(id)
        if(topico.isPresent){
            return ResponseEntity.ok(TopicoDto(atualizacaoTopicoForm.atualizar(id, topicoRepository)))
        }
        return ResponseEntity.notFound().build()
    }

    @Transactional
    @DeleteMapping("/{id}")
    fun remover(@PathVariable id: Long): ResponseEntity<Any>{
        val topico: Optional<Topico>  = topicoRepository.findById(id)
        if(topico.isPresent){
            topicoRepository.deleteById(id)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

}