package org.eventhub.main.service;

import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest category);
    CategoryResponse readById(long id);

    Category readByIdEntity(long id);
    CategoryResponse update(CategoryRequest category, long id);
    void delete(long id);
    List<CategoryResponse> getAll();
    Category getByName(String name);

}
