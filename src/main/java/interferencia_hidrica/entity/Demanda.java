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
}
