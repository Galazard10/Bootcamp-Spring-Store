package kz.bootcamp.springBoot.SpringBoot.controllers;

import kz.bootcamp.springBoot.SpringBoot.beans.DatabaseBean;
import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("dbBean")
    private DatabaseBean databaseBean;

    @GetMapping(value="/")
    public String homepage(Model model){
        List<ShopItems> shopItems = databaseBean.getAllShopItems();
        model.addAttribute("shopItems", shopItems);
        return "homepage";
    }

    @GetMapping(value="/additem")
    public String additempage(){
        return "additempage";
    }

    @PostMapping(value="/additem")
    public String toadditem(@RequestParam("itemName") String name,
                            @RequestParam("itemDesc") String description,
                            @RequestParam("itemPrice") double price,
                            @RequestParam("itemImgUrl") String imgUrl){

        ShopItems items = new ShopItems();
        items.setName(name);
        items.setDescription(description);
        items.setPrice(price);
        items.setImg_url(imgUrl);

        databaseBean.addShopItem(items);

        return "redirect:/additem?success";
    }

    @GetMapping(value="/details/{itemId}")
    public String detailspage(Model model, @PathVariable(name = "itemId") Long id){
        ShopItems item = databaseBean.getShopItem(id);
        model.addAttribute("shopItem", item);
        return "detailspage";
    }

    @PostMapping(value="/deleteitem")
    public String delete(@RequestParam(name = "itemId") Long id){
        databaseBean.deleteShopItem(id);
        return "redirect:/";
    }

    @PostMapping(value="/saveitem")
    public String save(@RequestParam(name = "itemId") Long id,
                       @RequestParam(name = "itemName") String name,
                       @RequestParam(name = "itemDesc") String desc,
                       @RequestParam(name = "itemPrice") double price,
                       @RequestParam(name = "itemImgUrl") String imgUrl){
        ShopItems item = databaseBean.getShopItem(id);
        item.setName(name);
        item.setDescription(desc);
        item.setPrice(price);
        item.setImg_url(imgUrl);
        databaseBean.updateShopItem(item);
        return "redirect:/details/" + id;
    }

}
