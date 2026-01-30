package com.alura.forohub.controller;

import com.alura.forohub.domain.curso.Curso;
import com.alura.forohub.domain.topico.DatosActualizarTopico;
import com.alura.forohub.domain.topico.DatosListadoTopico;
import com.alura.forohub.domain.topico.DatosRegistroTopico;
import com.alura.forohub.domain.topico.Topico;
import com.alura.forohub.domain.usuario.Usuario;
import com.alura.forohub.repository.CursoRepository;
import com.alura.forohub.repository.TopicoRepository;
import com.alura.forohub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {

        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(datosRegistroTopico.idUsuario());
        Optional<Curso> cursoBuscado = cursoRepository.findById(datosRegistroTopico.idCurso());

        if (usuarioBuscado.isEmpty() || cursoBuscado.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Usuario o Curso no encontrados");
        }

        // Crear la entidad Tópico con los datos recibidos
        Topico topico = new Topico(
                datosRegistroTopico.titulo(),
                datosRegistroTopico.mensaje(),
                usuarioBuscado.get(),
                cursoBuscado.get());

        // Guardar en Base de Datos
        topicoRepository.save(topico);

        return ResponseEntity.ok("Tópico creado con éxito: " + topico.getTitulo());
    }

    @GetMapping
    public List<DatosListadoTopico> listadoTopicos() {
        return topicoRepository.findAll().stream()
                .map(DatosListadoTopico::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> obtenerDatosTopico(@PathVariable Long id) {
        Optional<Topico> topicoBuscado = topicoRepository.findById(id);

        if (topicoBuscado.isPresent()) {
            return ResponseEntity.ok(new DatosListadoTopico(topicoBuscado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Optional<Topico> topicoBuscado = topicoRepository.findById(datosActualizarTopico.id());

        if (topicoBuscado.isPresent()) {
            Topico topico = topicoBuscado.get();

            topico.actualizarDatos(datosActualizarTopico);

            return ResponseEntity.ok(new DatosListadoTopico(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoBuscado = topicoRepository.findById(id);

        if (topicoBuscado.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}