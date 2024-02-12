package org.eventhub.main.service;


import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Category;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceImplTest {
    private final CategoryService categoryService;

    @Autowired
    public CategoryServiceImplTest(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Test
    public void createValidCategoryTest() {
        CategoryRequest request = new CategoryRequest("New Category");
        CategoryResponse response = categoryService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(request.getName(),response.getName());
        Assertions.assertEquals(3, categoryService.getAll().size());

        categoryService.delete(response.getId());
    }
    @Test
    public void createInvalidCategoryTest(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> categoryService.create(null));
    }
    @Test
    public void readByIdValidCategoryResponseTest(){
        CategoryResponse response = categoryService.readById(10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Sport", response.getName());
        Assertions.assertEquals(2, categoryService.getAll().size());
    }
    @Test
    public void readByIdInvalidCategoryResponseTest(){
        Assertions.assertThrows(NullEntityReferenceException.class, () -> categoryService.readById(100));
    }
    @Test
    public void readByIdEntityValidCategoryTest(){
        Category category = categoryService.readByIdEntity(10);
        Assertions.assertNotNull(category);
        Assertions.assertEquals("Sport", category.getName());
        Assertions.assertEquals(2, categoryService.getAll().size());
    }
    @Test
    public void readByIdEntityInvalidCategoryTest(){
        Assertions.assertThrows(NullEntityReferenceException.class, () -> categoryService.readByIdEntity(100));
    }
    @Test
    public void updateValidCategoryTest(){
        CategoryResponse response = categoryService.create(new CategoryRequest("Tested Category"));
        CategoryRequest request = new CategoryRequest("Updated Category");
        CategoryResponse updatedResponse = categoryService.update(request, response.getId());

        Assertions.assertNotNull(updatedResponse);
        Assertions.assertEquals(request.getName(), updatedResponse.getName());
        Assertions.assertEquals(3, categoryService.getAll().size());

        categoryService.delete(response.getId());
    }
    @Test
    void updateInvalidCategoryTest1(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> categoryService.update(null, 10));
    }
    @Test
    void updateInvalidCategoryTest2(){
        Assertions.assertThrows(NullEntityReferenceException.class, () -> categoryService.update(new CategoryRequest("Updated name"), 100));
    }
    @Test
    void deleteValidCategoryTest(){
        CategoryRequest request1 = new CategoryRequest("Deleted Category 1");
        CategoryResponse response1 = categoryService.create(request1);

        CategoryRequest request2 = new CategoryRequest("Deleted Category 2");
        CategoryResponse response2 = categoryService.create(request2);

        Assertions.assertEquals(4, categoryService.getAll().size());

        categoryService.delete(response1.getId());
        categoryService.delete(response2.getId());

        Assertions.assertEquals(2, categoryService.getAll().size());
    }
    @Test
    void deleteInvalidCategoryTest(){
        Assertions.assertThrows(NullEntityReferenceException.class, () -> categoryService.delete(100));
    }
    @Test
    void getAllValidCategoryTest(){
        CategoryResponse response = categoryService.create(new CategoryRequest("Created Category"));
        List<CategoryResponse> all = categoryService.getAll();

        Assertions.assertEquals(3, all.size());
        Assertions.assertEquals("Sport", all.get(0).getName());
        Assertions.assertEquals("Charity", all.get(1).getName());
        Assertions.assertEquals("Created Category", all.get(2).getName());

        categoryService.delete(response.getId());
    }
}

