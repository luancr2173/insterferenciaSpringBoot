package interferencia_hidrica.repository;

import interferencia_hidrica.entity.Interferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterferenciaRepository extends JpaRepository<Interferencia, Long> {
    
}
