package com.apigateway.dto;

import java.util.List;

public class AutorDTO {
    private Long id;
    private Integer numero;
    private String nome;
    private List<LivroDTO> livros;

    public AutorDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<LivroDTO> getLivros() { return livros; }
    public void setLivros(List<LivroDTO> livros) { this.livros = livros; }
}