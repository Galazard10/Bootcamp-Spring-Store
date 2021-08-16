package kz.bootcamp.springBoot.SpringBoot.repositories;

import kz.bootcamp.springBoot.SpringBoot.entities.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ShopBrandsRepository extends JpaRepository<Brands, Long> {
}