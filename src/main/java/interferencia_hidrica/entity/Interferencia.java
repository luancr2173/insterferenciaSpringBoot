package interferencia_hidrica.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Interferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "interferencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Finalidade> finalidades;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public List<Finalidade> getFinalidades() { return finalidades; }
    public void setFinalidades(List<Finalidade> finalidades) { this.finalidades = finalidades; }
}
