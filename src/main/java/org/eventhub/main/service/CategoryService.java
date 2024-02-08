package org.eventhub.main.service;

import org.eventhub.main.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category readById(long id);
    Category update(Category category);
    void delete(long id);
    List<Category> getAll();
}
