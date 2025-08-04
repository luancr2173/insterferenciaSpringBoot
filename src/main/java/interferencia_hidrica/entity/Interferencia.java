package interferencia_hidrica.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Interferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "interferencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Finalidade> finalidades;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Finalidade> getFinalidades() {
        return finalidades;
    }

    public void setFinalidades(List<Finalidade> finalidades) {
        this.finalidades = finalidades;
    }
}
