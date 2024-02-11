package org.eventhub.main.mapper;

import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryResponse entityToResponse(Category category) {
        if (category == null) {
            throw new NullEntityReferenceException("Category can't be null");
        }
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category requestToEntity(CategoryRequest request, Category category){
        if(request == null){
            throw new NullDtoReferenceException("Request can't be null");
        }
        if(category == null){
            throw new NullEntityReferenceException("Category can't be null");
        }
        category.setId(request.getId());
        category.setName(request.getName());
        return category;
    }
}
