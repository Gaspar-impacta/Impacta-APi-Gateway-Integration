package com.apigateway.controller;

import com.apigateway.dto.AutorDTO;
import com.apigateway.dto.AutorSimplesDTO;
import com.apigateway.dto.LivroDTO;
import com.apigateway.exception.ResourceNotFoundException;
import com.apigateway.mapper.AutorMapper;
import com.apigateway.model.Autor;
import com.apigateway.model.Livro;
import com.apigateway.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/autor")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorDTO> criarAutor(@RequestBody AutorDTO dto) {
        Autor autor = AutorMapper.toEntity(dto);
        Autor salvo = autorService.salvarAutor(autor);
        return ResponseEntity.ok(AutorMapper.toDTO(salvo));
    }

    @GetMapping("/{numero}")
    public ResponseEntity<AutorSimplesDTO> buscarPorNumero(@PathVariable Integer numero) {
        var autor = autorService.buscarPorNumero(numero)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com numero: " + numero));
        return ResponseEntity.ok(AutorMapper.toSimplesDTO(autor));
    }

    @GetMapping
    public ResponseEntity<List<AutorSimplesDTO>> listarAutores() {
        List<AutorSimplesDTO> lista = autorService.listarAutoresSemLivros();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{numero}/livro")
    public ResponseEntity<LivroDTO> adicionarLivro(@PathVariable Integer numero, @RequestBody LivroDTO dto) {
        Livro livro = AutorMapper.toLivroEntity(dto);
        Livro salvo = autorService.adicionarLivro(numero, livro)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com numero: " + numero));
        return ResponseEntity.ok(AutorMapper.toLivroDTO(salvo));
    }

    @GetMapping("/{numero}/livro")
    public ResponseEntity<List<LivroDTO>> listarLivros(@PathVariable Integer numero) {
        List<LivroDTO> lista = autorService.listarLivrosPorAutor(numero).stream()
                .map(AutorMapper::toLivroDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
