package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.mapper.CategoryMapper;
import org.eventhub.main.model.Category;
import org.eventhub.main.repository.CategoryRepository;
import org.eventhub.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        if (categoryRequest != null) {
            Category category = categoryMapper.requestToEntity(categoryRequest, new Category());
            return categoryMapper.entityToResponse(categoryRepository.save(category));
        }
        throw new NullDtoReferenceException("Request cannot be 'null'");
    }

    @Override
    public CategoryResponse readById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found"));
        return categoryMapper.entityToResponse(category);
    }

    @Override
    public Category readByIdEntity(long id){
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, long id) {
        if (categoryRequest != null) {
            Category existingCategory = readByIdEntity(id);// to check if category exist
            Category category = categoryMapper.requestToEntity(categoryRequest, existingCategory);
            return categoryMapper.entityToResponse(categoryRepository.save(category));
        }
        throw new NullDtoReferenceException("Updated Category Request cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(readByIdEntity(id));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::entityToResponse)
                .collect(Collectors.toList());
    }

}
