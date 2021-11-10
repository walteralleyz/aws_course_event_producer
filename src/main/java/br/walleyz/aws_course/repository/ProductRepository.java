package br.walleyz.aws_course.repository;


import br.walleyz.aws_course.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Optional<Product> findByCode(String code);
}
