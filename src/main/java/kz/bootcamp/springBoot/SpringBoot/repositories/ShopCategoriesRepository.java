package kz.bootcamp.springBoot.SpringBoot.repositories;

import kz.bootcamp.springBoot.SpringBoot.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShopCategoriesRepository extends JpaRepository<Categories, Long> {

}
