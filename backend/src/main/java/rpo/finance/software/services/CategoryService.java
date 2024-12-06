package rpo.finance.software.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rpo.finance.software.DTO.user.CategoryDTO;
import rpo.finance.software.entities.Category;
import rpo.finance.software.entities.User;
import rpo.finance.software.repositories.CategoryRepository;
import rpo.finance.software.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id).map(this::mapToDTO);
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setColor(dto.color());
        category.setUserID(getUserById(dto.userID()));

        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    public Optional<CategoryDTO> updateCategory(Long id, CategoryDTO dto) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(dto.name());
            existingCategory.setColor(dto.color());
            existingCategory.setUserID(getUserById(dto.userID()));

            Category updatedCategory = categoryRepository.save(existingCategory);
            return mapToDTO(updatedCategory);
        });
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /*
    mapToDTO — это метод для "перекладывания" данных из сложной сущности
    в упрощённый объект для передачи клиенту через API.
    */
    private CategoryDTO mapToDTO(Category category) {
        return new CategoryDTO(
                category.getCategoryID(),
                category.getUserID().getUserID(),
                category.getName(),
                category.getColor()
        );
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + userId + " не найден."));
    }
}
