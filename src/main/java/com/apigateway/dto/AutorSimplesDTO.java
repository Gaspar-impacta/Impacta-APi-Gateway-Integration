package com.apigateway.dto;

public class AutorSimplesDTO {

    private Long id;
    private Integer numero;
    private String nome;

    public AutorSimplesDTO() {
    }

    public AutorSimplesDTO(Long id, Integer numero, String nome) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
