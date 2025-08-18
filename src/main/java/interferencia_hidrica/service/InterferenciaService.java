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
        // garante que as finalidades apontem para a interferência
        if (interferencia.getFinalidades() != null) {
            interferencia.getFinalidades().forEach(f -> f.setInterferencia(interferencia));
        }
        return repository.save(interferencia);
    }

    public List<Interferencia> listar() {
        return repository.findAll();
    }

    public Optional<Interferencia> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        Interferencia interferencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interferência não encontrada"));

        // Limpa a lista de finalidades para garantir exclusão
        if (interferencia.getFinalidades() != null) {
            interferencia.getFinalidades().clear();
        }

        repository.delete(interferencia);
    }
}
