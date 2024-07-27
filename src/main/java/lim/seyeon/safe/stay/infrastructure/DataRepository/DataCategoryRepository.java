package lim.seyeon.safe.stay.infrastructure.DataRepository;

import lim.seyeon.safe.stay.domain.Category.Category;
import lim.seyeon.safe.stay.domain.Category.CategoryRepository;
import lim.seyeon.safe.stay.domain.Exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataCategoryRepository implements CategoryRepository {

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DataCategoryRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Category add(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(category);
        namedParameterJdbcTemplate.update(
                "INSERT INTO categories (id, name, description) VALUES (:id, :name, :description)",
                namedParameter, keyHolder
        );
        Long generatedId = keyHolder.getKey().longValue();
        category.setId(generatedId);
        return category;
    }

    public Category findCategoryById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Category category = null;

        try {
            category = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM categories WHERE id = :id",
                    namedParameter, new BeanPropertyRowMapper<>(Category.class)
            );
        } catch(EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }
        return category;
    }

    public Category findCategoryByName(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", name);
        Category category = null;
        try {
            category = namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM categories WHERE name = :name",
                    namedParameter, new BeanPropertyRowMapper<>(Category.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Category with name " + name + " not found");
        }
        return category;
    }

    public List<Category> findAllCategories() {
        List<Category> categories = namedParameterJdbcTemplate.query(
                "SELECT * FROM categories",
                new BeanPropertyRowMapper<>(Category.class)
        );
        return categories;
    }

    public Category update(Category category) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(category);
        int rowsAffected = namedParameterJdbcTemplate.update(
                "UPDATE categories SET name = :name, description = :description WHERE id = :id",
                namedParameter
        );
        if(rowsAffected == 0) {
            throw new RuntimeException("Category with id " + category.getId() + " not found");
        }
        return category;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(
                "DELETE FROM categories WHERE id = :id", namedParameter
        );
    }
}
