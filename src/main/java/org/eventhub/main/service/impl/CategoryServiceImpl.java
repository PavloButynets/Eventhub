package org.eventhub.main.service.impl;

import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Category;
import org.eventhub.main.repository.CategoryRepository;
import org.eventhub.main.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category create(Category category) {
        if (category != null) {
            return categoryRepository.save(category);
        }
        throw new NullEntityReferenceException("Created Category cannot be 'null'");
    }

    @Override
    public Category readById(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NullEntityReferenceException("Category with id " + id + " not found"));
    }

    @Override
    public Category update(Category category) {
        if (category != null) {
            readById(category.getId());
            return categoryRepository.save(category);
        }
        throw new NullEntityReferenceException("Updated Category cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(readById(id));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
