package org.eventhub.main.service;


import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {
    private final CategoryService categoryService;

    @Autowired
    public CategoryServiceTest(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Test
    public void createValidCategoryTest() {
        CategoryRequest request = new CategoryRequest("New Category");
        CategoryResponse response = categoryService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getName(), request.getName());
        Assertions.assertEquals(categoryService.getAll().size(), 3);

        categoryService.delete(response.getId());
    }



}

