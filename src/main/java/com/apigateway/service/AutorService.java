package com.apigateway.service;

import com.apigateway.dto.AutorSimplesDTO;
import com.apigateway.exception.ResourceAlreadyExistsException;
import com.apigateway.exception.ResourceNotFoundException;
import com.apigateway.mapper.AutorMapper;
import com.apigateway.model.Autor;
import com.apigateway.model.Livro;
import com.apigateway.repository.AutorRepository;
import com.apigateway.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public Autor salvarAutor(Autor autor) {
        boolean exists = autorRepository.findByNumero(autor.getNumero()).isPresent();
        if (exists) {
            throw new ResourceAlreadyExistsException(
                    "Já existe um autor cadastrado com o número: " + autor.getNumero()
            );
        }
        return autorRepository.save(autor);
    }

    public Optional<Autor> buscarPorNumero(Integer numero) {
        return autorRepository.findByNumero(numero);
    }

    public List<AutorSimplesDTO> listarAutoresSemLivros() {
        return autorRepository.findAll().stream()
                .map(AutorMapper::toSimplesDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Livro> adicionarLivro(Integer numeroAutor, Livro livro) {
        boolean livroExiste = livroRepository.findByNumero(livro.getNumero()).isPresent();

        if (livroExiste) {
            throw new ResourceAlreadyExistsException(
                    "Já existe um livro cadastrado com o número: " + livro.getNumero()
            );
        }
        
        return autorRepository.findByNumero(numeroAutor)
                .map(autor -> {
                    livro.setAutor(autor);
                    return livroRepository.save(livro);
                });
    }
    public List<Livro> listarLivrosPorAutor(Integer numeroAutor) {
        Autor autor = autorRepository.findByNumero(numeroAutor)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Autor não encontrado com numero: " + numeroAutor
                ));
        return livroRepository.findByAutor(autor);
    }
}
