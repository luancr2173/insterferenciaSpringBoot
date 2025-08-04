package interferencia_hidrica.service;

import interferencia_hidrica.entity.Interferencia;
import interferencia_hidrica.repository.InterferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterferenciaService {

    private final InterferenciaRepository repo;

    public Interferencia salvar(Interferencia interferencia) {
        return repo.save(interferencia);
    }

    public List<Interferencia> listar() {
        return repo.findAll();
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }

    public Interferencia buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
}
