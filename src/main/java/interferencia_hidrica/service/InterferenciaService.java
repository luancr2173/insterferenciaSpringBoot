package interferencia_hidrica.service;

import interferencia_hidrica.entity.Interferencia;
import interferencia_hidrica.repository.InterferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterferenciaService {

    private final InterferenciaRepository repository;

    public Interferencia salvar(Interferencia interferencia) {
        return repository.save(interferencia);
    }

    public List<Interferencia> listar() {
        return repository.findAll();
    }

    public Optional<Interferencia> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
