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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
