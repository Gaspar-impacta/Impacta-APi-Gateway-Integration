package com.apigateway.mapper;

import com.apigateway.dto.AutorDTO;
import com.apigateway.dto.AutorSimplesDTO;
import com.apigateway.dto.LivroDTO;
import com.apigateway.model.Autor;
import com.apigateway.model.Livro;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class AutorMapper {

    public static AutorDTO toDTO(Autor autor) {
        if (autor == null) return null;
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNumero(autor.getNumero());
        dto.setNome(autor.getNome());
        dto.setLivros(
            Optional.ofNullable(autor.getLivros())
                    .orElseGet(ArrayList::new)
                    .stream()
                    .map(AutorMapper::toLivroDTO)
                    .collect(Collectors.toList())
        );
        return dto;
    }

    public static AutorSimplesDTO toSimplesDTO(Autor autor) {
        if (autor == null) return null;
        AutorSimplesDTO dto = new AutorSimplesDTO();
        dto.setId(autor.getId());
        dto.setNumero(autor.getNumero());
        dto.setNome(autor.getNome());
        return dto;
    }

    
    public static LivroDTO toLivroDTO(Livro livro) {
        if (livro == null) return null;
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setNumero(livro.getNumero());
        dto.setTitulo(livro.getTitulo());
        dto.setEdicao(livro.getEdicao());
        dto.setIsbn(livro.getIsbn());
        dto.setCategoria(livro.getCategoria());
        return dto;
    }

    public static Autor toEntity(AutorDTO dto) {
        if (dto == null) return null;
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setNumero(dto.getNumero());
        autor.setNome(dto.getNome());
        return autor;
    }

    public static Livro toLivroEntity(LivroDTO dto) {
        if (dto == null) return null;
        Livro livro = new Livro();
        livro.setId(dto.getId());
        livro.setNumero(dto.getNumero());
        livro.setTitulo(dto.getTitulo());
        livro.setEdicao(dto.getEdicao());
        livro.setIsbn(dto.getIsbn());
        livro.setCategoria(dto.getCategoria());
        return livro;
    }
}
