package com.codewithmosh.store.products;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(required = false) Byte categoryId) {
        List<Product> products = null;

        if (categoryId == null) {
            products = productRepository.findAllProductsWithCategory();
        } else {
            products = productRepository.findProductsByCategoryId(categoryId);
        }

        var prods = products.stream().map(product -> productMapper.toDto(product))
                .toList();
        return ResponseEntity.ok(prods);

    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        var product = productMapper.toEntity(productDto);
        var category = categoryRepository.findById(Byte.valueOf(productDto.getCategoryId())).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto productDto,
            @PathVariable(name = "id") Long productId) {
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        if (productDto.getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(Byte.valueOf(productDto.getCategoryId())).orElse(null));
        }
        productMapper.updateEntity(productDto, product);
        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long productId) {
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }
}
