package interferencia_hidrica.controller;

import interferencia_hidrica.entity.Finalidade;
import interferencia_hidrica.repository.FinalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finalidades")
@RequiredArgsConstructor
public class FinalidadeController {

    private final FinalidadeRepository repository;

    @PostMapping
    public ResponseEntity<Finalidade> criar(@RequestBody Finalidade finalidade) {
        return ResponseEntity.ok(repository.save(finalidade));
    }

    @GetMapping
    public ResponseEntity<List<Finalidade>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Finalidade> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Finalidade> atualizar(@PathVariable Long id, @RequestBody Finalidade finalidadeAtualizada) {
        return repository.findById(id)
                .map(finalidade -> {
                    finalidade.setNome(finalidadeAtualizada.getNome());
                    // adicione aqui outros campos se a entidade tiver mais atributos
                    return ResponseEntity.ok(repository.save(finalidade));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
