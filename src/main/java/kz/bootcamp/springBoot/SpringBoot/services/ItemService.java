package kz.bootcamp.springBoot.SpringBoot.services;

import kz.bootcamp.springBoot.SpringBoot.entities.Brands;
import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;

import java.util.List;

public interface ItemService {

    List<ShopItems> getAllItems();
    Brands getBrandById(Long id);
    ShopItems addShopItem(ShopItems item);

}
