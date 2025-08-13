package interferencia_hidrica.controller;

import interferencia_hidrica.entity.Interferencia;
import interferencia_hidrica.service.InterferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interferencias")
@RequiredArgsConstructor
public class InterferenciaController {

    private final InterferenciaService service;

    @PostMapping
    public ResponseEntity<Interferencia> criar(@RequestBody Interferencia interferencia) {
        return ResponseEntity.ok(service.salvar(interferencia));
    }

    @GetMapping
    public ResponseEntity<List<Interferencia>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interferencia> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interferencia> atualizar(@PathVariable Long id, @RequestBody Interferencia interferencia) {
        return service.buscarPorId(id)
                .map(existingInterferencia -> {
                    existingInterferencia.setDescricao(interferencia.getDescricao());
                    existingInterferencia.setLatitude(interferencia.getLatitude());
                    existingInterferencia.setLongitude(interferencia.getLongitude());

                    // Atualizando finalidades (substituindo toda a lista)
                    if (interferencia.getFinalidades() != null) {
                        existingInterferencia.getFinalidades().clear();
                        existingInterferencia.getFinalidades().addAll(interferencia.getFinalidades());
                    }

                    Interferencia atualizado = service.salvar(existingInterferencia);
                    return ResponseEntity.ok(atualizado);
                })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
