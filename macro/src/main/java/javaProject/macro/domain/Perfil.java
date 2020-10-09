package javaProject.macro.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Perfil {
    private Long id;
    private String nome;

    public Perfil() {

    }

    public Perfil(String nome) {
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
