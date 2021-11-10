package br.walleyz.aws_course.controller;

import br.walleyz.aws_course.enums.EventType;
import br.walleyz.aws_course.model.Product;
import br.walleyz.aws_course.repository.ProductRepository;
import br.walleyz.aws_course.service.ProductPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;
    private final ProductPublisher publisher;

    @Autowired
    public ProductController(ProductRepository repository, ProductPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable int id) {
        Optional<Product> optionalProduct = repository.findById(id);

        return optionalProduct.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-code")
    public ResponseEntity<Product> findByCode(@RequestParam String code) {
        Optional<Product> optionalProduct = repository.findByCode(code);

        return optionalProduct.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product created = repository.save(product);

        publisher.publishEvent(created, EventType.PRODUCT_CREATED);

        return ResponseEntity.created(URI.create(String.valueOf(created.getId()))).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable int id) {
        if(repository.existsById(id)) {
            product.setId(id);

            Product updated = repository.save(product);
            publisher.publishEvent(updated, EventType.PRODUCT_UPDATED);

            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            repository.delete(product);
            publisher.publishEvent(product, EventType.PRODUCT_DELETED);

            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
    }
}
