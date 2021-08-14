package kz.bootcamp.springBoot.SpringBoot.beans;

import kz.bootcamp.springBoot.SpringBoot.entities.Brands;
import kz.bootcamp.springBoot.SpringBoot.entities.Categories;
import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopBrandsRepository;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopCategoriesRepository;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class DatabaseBean {

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private ShopCategoriesRepository shopCategoriesRepository;

    @Autowired
    private ShopBrandsRepository shopBrandsRepository;

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

    public List<Categories> getAllCategories(){
        return shopCategoriesRepository.findAll();
    }

    public Categories getCategoryById(Long id){
        return shopCategoriesRepository.findById(id).orElse(null);
    }

    public List<Brands> getAllBrands(){
        return shopBrandsRepository.findAll();
    }

    public Brands getBrandById(Long id){
        return shopBrandsRepository.findById(id).orElse(null);
    }
}
