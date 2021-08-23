package kz.bootcamp.springBoot.SpringBoot.services;

import kz.bootcamp.springBoot.SpringBoot.entities.Brands;
import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopBrandsRepository;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private ShopBrandsRepository shopBrandsRepository;

    @Override
    public List<ShopItems> getAllItems() {
        return shopItemsRepository.findByPriceGreaterThanEqual(0);
    }

    @Override
    public Brands getBrandById(Long id) {
        return shopBrandsRepository.findById(id).orElse(null);
    }

    @Override
    public ShopItems addShopItem(ShopItems item) {
        return shopItemsRepository.save(item);
    }
}
