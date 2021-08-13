package kz.bootcamp.springBoot.SpringBoot.repositories;

import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ShopItemsRepository extends JpaRepository<ShopItems, Long> {

    List<ShopItems> findByPriceGreaterThanEqual(double price);

}
