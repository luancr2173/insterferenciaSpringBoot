package interferencia_hidrica.controller;

import interferencia_hidrica.entity.Demanda;
import interferencia_hidrica.repository.DemandaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demandas")
@RequiredArgsConstructor
public class DemandaController {

    private final DemandaRepository repository;

    @PostMapping
    public ResponseEntity<Demanda> criar(@RequestBody Demanda demanda) {
        return ResponseEntity.ok(repository.save(demanda));
    }

    @GetMapping
    public ResponseEntity<List<Demanda>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demanda> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Demanda> atualizar(@PathVariable Long id, @RequestBody Demanda demandaAtualizada) {
        return repository.findById(id)
                .map(demanda -> {
                    demanda.setNome(demandaAtualizada.getNome());
                    demanda.setVazaoMensal(demandaAtualizada.getVazaoMensal());
                    demanda.setFinalidade(demandaAtualizada.getFinalidade());
                    return ResponseEntity.ok(repository.save(demanda));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
