package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryResponse> create(@Validated @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        CategoryResponse categoryResponse = categoryService.create(categoryRequest);
        log.info("**/created category(id) = " + categoryResponse.getId());

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("category_id") long categoryId,
                                                   @Validated @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        CategoryResponse categoryResponse = categoryService.update(categoryRequest, categoryId);
        log.info("**/updated category(id) = " + categoryId);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("category_id") long categoryId) {
        log.info("**/deleted category(id) = " + categoryId);
        categoryService.delete(categoryId);
        return new ResponseEntity<>(new OperationResponse("Category deleted successfully"), HttpStatus.OK);
    }
}
