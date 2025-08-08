package interferencia_hidrica.repository;

import interferencia_hidrica.entity.Finalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalidadeRepository extends JpaRepository<Finalidade, Long> {
}
