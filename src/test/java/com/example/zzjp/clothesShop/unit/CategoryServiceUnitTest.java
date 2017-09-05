package com.example.zzjp.clothesShop.unit;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.dto.CategoryDto;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceUnitTest {
    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @Before
    public void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void shouldGetCategoryByName() {
        String name = "skirt";
        Category category = new Category(name);

        when(categoryRepository.findByName(name))
                .thenReturn(category);

        Category result = categoryService.getByName(name);

        verify(categoryRepository)
                .findByName(name);

        assertThat(result)
                .isEqualTo(category);
    }

    @Test
    public void shouldGetCategoryById() {
        Long id = 213L;
        Category category = new Category();

        when(categoryRepository.findOne(id))
                .thenReturn(category);

        Category result = categoryService.getById(id);

        verify(categoryRepository)
                .findOne(id);

        assertThat(result)
                .isEqualTo(category);
    }

    @Test
    public void shouldGetAllCategories() {
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll())
                .thenReturn(categories);

        List<Category> result = categoryService.getAll();

        verify(categoryRepository)
                .findAll();

        assertThat(result)
                .isNotEmpty()
                .isEqualTo(categories);
        assertThat(result.containsAll(categories))
                .isTrue();
        assertThat(result.size())
                .isEqualTo(2);
    }

    @Test
    public void shouldAddCategory() {
        String name = "shorts";
        CategoryDto categoryDto = new CategoryDto(name);
        Category category = new Category(categoryDto);

        when(categoryRepository.save(category))
                .thenReturn(category);

        Category result = categoryService.add(categoryDto);

        verify(categoryRepository)
                .save(category);

        assertThat(result)
                .isEqualTo(category);
        assertThat(result.getName())
                .isEqualTo(name);
    }

    @Test
    public void shouldUpdateCategory() {
        Long id = 143L;
        String name = "shorts";
        String newName = "trousers";
        Category category = new Category();
        category.setId(id);
        category.setName(name);

        when(categoryRepository.findOne(id))
                .thenReturn(category);
        when(categoryRepository.save(category))
                .thenReturn(category);

        Category result = categoryService.update(id, newName);

        verify(categoryRepository)
                .findOne(id);
        verify(categoryRepository)
                .save(category);

        assertThat(result)
                .isEqualTo(category);
        assertThat(result.getId())
                .isEqualTo(category.getId());
        assertThat(result.getName())
                .isNotEqualTo(name);
        assertThat(result.getName())
                .isEqualTo(newName);
    }

    @Test
    public void shouldRemoveCategory() {
        Long id = 143L;
        Category category = new Category();
        category.setId(id);

        categoryService.remove(id);

        verify(categoryRepository)
                .delete(id);
    }
}
