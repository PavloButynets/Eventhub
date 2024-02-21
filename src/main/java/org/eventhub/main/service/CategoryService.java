package org.eventhub.main.service;

import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse create(CategoryRequest category);
    CategoryResponse readById(UUID id);

    Category readByIdEntity(UUID id);
    CategoryResponse update(CategoryRequest category, UUID id);
    void delete(UUID id);
    List<CategoryResponse> getAll();
    Category getByName(String name);

    List<CategoryResponse> getAllByEventId(UUID eventId);

}
