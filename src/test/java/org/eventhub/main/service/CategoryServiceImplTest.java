package org.eventhub.main.service;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceImplTest {
    private final CategoryService categoryService;

    public CategoryServiceImplTest(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Test
    public void testCreateToDo() {
        Togi Do toDo = createTestToDo();
        ToDo savedToDo = toDoRepository.save(toDo);

        Assertions.assertNotNull(savedToDo);
        Assertions.assertEquals(toDo.getTitle(), savedToDo.getTitle());
        Assertions.assertEquals(toDo.getId(), savedToDo.getId());
        Assertions.assertEquals(toDo.getOwner(), savedToDo.getOwner());

        toDoRepository.deleteById(savedToDo.getId());
    }

    @Test
    public void testUpdateToDo() {
        ToDo toDo = createTestToDo();
        ToDo savedToDo = toDoRepository.save(toDo);

        Assertions.assertEquals(toDo.getTitle(), savedToDo.getTitle());

        toDo.setTitle("Modified title");
        ToDo modifiedToDo = toDoRepository.save(toDo);

        Assertions.assertEquals(toDo.getTitle(), modifiedToDo.getTitle());
    }

    @Test
    public void testDeleteToDo() {
        ToDo toDo = createTestToDo();
        ToDo savedToDo = toDoRepository.save(toDo);

        toDoRepository.deleteById(savedToDo.getId());

        Optional<ToDo> deletedToDo = toDoRepository.findById(savedToDo.getId());
        Assertions.assertTrue(deletedToDo.isEmpty());
    }

    private ToDo createTestToDo() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Title");
        toDo.setOwner(user);
        toDo.setCreatedAt(LocalDateTime.now());

        return toDo;
    }
}

