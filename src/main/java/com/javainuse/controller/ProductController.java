package com.javainuse.controller;

import com.javainuse.dao.ProductRepository;
import com.javainuse.model.Product;
import com.javainuse.model.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    //Dependency Injection
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
        //Return object with data message status:
    ResponseEntity<ResponseObject> findById(@PathVariable("id") Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(true,
                            "found product successfully",
                            foundProduct));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(false,
                        "Cannot find product with id = " + id,
                        null));
    }

    //Insert product with POST Mapping
    //Postman: raw/json
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> addProduct(@RequestBody Product product) {
        //2 Product must not have the same name
        List<Product> foundProduct = productRepository.findByName(product.getName().trim());
        if (foundProduct.size() > 0) return ResponseEntity.
                status(HttpStatus.ALREADY_REPORTED).
                body(new ResponseObject(false,
                        "Product name already taken",
                        null));
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseObject(true,
                        "Add product success",
                        productRepository.save(product)));
    }

    //Update, upsert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable("id") long id, @RequestBody Product newProduct) {
        Product updatedProduct = productRepository.findById(id).map(product -> {
            product.setBrandName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setName(newProduct.getName());
            return productRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return productRepository.save(newProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseObject(true,
                        "update Data Successfully",
                        updatedProduct));
    }
}
