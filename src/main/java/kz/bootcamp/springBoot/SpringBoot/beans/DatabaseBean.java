package kz.bootcamp.springBoot.SpringBoot.beans;

import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public class DatabaseBean {

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    public List<ShopItems> getAllShopItems(){
//        return shopItemsRepository.findAll();
        return shopItemsRepository.findByPriceGreaterThanEqual(0);
    }

    public ShopItems getShopItem(Long id){
        //return shopItemsRepository.getById(id);
        Optional<ShopItems> optional = shopItemsRepository.findById(id);
        return optional.orElse(null);
    }

    public void addShopItem(ShopItems shopItem){
        shopItemsRepository.save(shopItem);
    }

    public void deleteShopItem(Long id){
        shopItemsRepository.deleteById(id);
    }

    public void updateShopItem(ShopItems shopItem){
        shopItemsRepository.save(shopItem);
    }


}
