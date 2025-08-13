package interferencia_hidrica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Finalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "interferencia_id")
    @JsonBackReference
    private Interferencia interferencia;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Interferencia getInterferencia() { return interferencia; }
    public void setInterferencia(Interferencia interferencia) { this.interferencia = interferencia; }
}
