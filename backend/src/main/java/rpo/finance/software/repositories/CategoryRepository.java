package rpo.finance.software.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rpo.finance.software.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
