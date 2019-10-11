package com.todo1.hulkstore.repository;

import com.todo1.hulkstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
