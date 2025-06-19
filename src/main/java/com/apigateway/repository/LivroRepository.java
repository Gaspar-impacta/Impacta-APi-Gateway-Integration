package com.apigateway.repository;

import com.apigateway.model.Autor;
import com.apigateway.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByAutorNumero(Integer numero);
    List<Livro> findByAutor(Autor autor);
    Optional<Livro> findByNumero(Integer numero);
}