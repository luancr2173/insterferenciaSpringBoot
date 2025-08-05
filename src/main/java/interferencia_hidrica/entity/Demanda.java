package interferencia_hidrica.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Demanda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal vazaoMensal;

    @ManyToOne
    @JoinColumn(name = "finalidade_id")
    private Finalidade finalidade;

    // Getters e Setters
    public Long getId (){
        return id;
    } 

    public void setId(Long id){
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public BigDecimal getVazaoMensal(){
        return vazaoMensal;
    }

    public void setVazaoMensal(BigDecimal vazaoMensal) {
        this.vazaoMensal = vazaoMensal;
    }

}
