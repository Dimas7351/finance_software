package rpo.finance.software.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rpo.finance.software.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
