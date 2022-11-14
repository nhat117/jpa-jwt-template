package com.javainuse.database;

import com.javainuse.dao.ProductRepository;
import com.javainuse.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBase {
    //Logger
    private static  final Logger logger = LoggerFactory.getLogger(DataBase.class);

    @Bean
    CommandLineRunner initDbRunner(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                logger.info("insert data : " + productRepository.save(new Product(1L, "Macbook", "Macbook Pro 16 inch", "Apple")));
                logger.info("insert data : " + productRepository.save(new Product(2L, "Macbook", "Macbook Pro 14 inch", "Apple")));
                logger.info("insert data : " + productRepository.save(new Product(3L, "Macbook", "Macbook Pro 13 inch", "Apple")));

            }
        };
    }
}
