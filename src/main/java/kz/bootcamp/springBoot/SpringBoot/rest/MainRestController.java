package kz.bootcamp.springBoot.SpringBoot.rest;

import kz.bootcamp.springBoot.SpringBoot.entities.Brands;
import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopItemsRepository;
import kz.bootcamp.springBoot.SpringBoot.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins = {"http://localhost"})
public class MainRestController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value="/allitems")
    public ResponseEntity<List<ShopItems>> getAllItems(){
        List<ShopItems> shopItems = itemService.getAllItems();
        return new ResponseEntity<>(shopItems, HttpStatus.OK);
    }

    @PostMapping(value="/additem")
    public ResponseEntity<String> addItem(@RequestParam(name="name") String name,
                                          @RequestParam(name="description") String description,
                                          @RequestParam(name="price") double price,
                                          @RequestParam(name="brand_id") Long brandId){
        Brands brand = itemService.getBrandById(brandId);
        if(brand != null){

            ShopItems items = new ShopItems();
            items.setName(name);
            items.setDescription(description);
            items.setPrice(price);
            items.setBrand(brand);
            itemService.addShopItem(items);
            return new ResponseEntity<>("ADDED", HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR", HttpStatus.OK);
    }
}
