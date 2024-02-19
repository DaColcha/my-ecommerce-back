package com.ecommerce.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired // Significa que la instancia será inyectada por Spring automáticamente
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts() {
        // return Arrays.asList("iPhone", "Samsung", "Nokia");
        return productRepository.findAll();
    }

    @GetMapping("/getByName/{name}/{pageParam}")
    public List<Product> getProductsByName(@PathVariable String name, @PathVariable String pageParam) {
        Pageable pageable= PageRequest.of(Integer.parseInt(pageParam), 2);
        return productRepository.findByName(name, pageable);
        //return productRepository.findByNameLike("%"+name+"%");
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> optProduct = productRepository.findById(Integer.parseInt(id));
        if (optProduct.isPresent()) {
            // return optProduct.get();
            return ResponseEntity.ok(optProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadProducts() throws IOException{
        File file = new File("/Users/colchita/Library/CloudStorage/OneDrive-EscuelaPolitécnicaNacional/SEMESTRE_VI/Aplicaicones Web/Projects/my-ecommerce-back/test-data/products.csv");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body((Resource)resource);
    }

    @PostMapping("/uploadProduct")
    public String uploadProductsFromFile(@RequestParam("file") MultipartFile file) {
        try (InputStream is = file.getInputStream()) {

            BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is));

            while (br.ready()) {
                String line = br.readLine();
                String[] data = line.split(",");
                Product product = new Product();
                product.setId(Integer.parseInt(data[0]));
                product.setName(data[1]);
                product.setImageUrl(data[2]);
                System.out.println(product.toString());
            }

            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading the file: " + e.getMessage();
        }
        
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    @PatchMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        productRepository.deleteById(Integer.parseInt(id));
        return ResponseEntity.ok().build();
    }

}
