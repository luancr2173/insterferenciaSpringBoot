package interferencia_hidrica.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Finalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "interferencia_id")
    @JsonBackReference // evita loop no JSON
    private Interferencia interferencia;

    @OneToMany(mappedBy = "finalidade", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // lado "gerenciador" da relação
    private List<Demanda> demandas;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Interferencia getInterferencia() {
        return interferencia;
    }

    public void setInterferencia(Interferencia interferencia) {
        this.interferencia = interferencia;
    }

    public List<Demanda> getDemandas() {
        return demandas;
    }

    public void setDemandas(List<Demanda> demandas) {
        this.demandas = demandas;
    }
}
