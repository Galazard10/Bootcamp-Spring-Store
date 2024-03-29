package kz.bootcamp.springBoot.SpringBoot.controllers;

import kz.bootcamp.springBoot.SpringBoot.beans.DatabaseBean;
import kz.bootcamp.springBoot.SpringBoot.entities.Brands;
import kz.bootcamp.springBoot.SpringBoot.entities.Categories;
import kz.bootcamp.springBoot.SpringBoot.entities.ShopItems;
import kz.bootcamp.springBoot.SpringBoot.entities.Users;
import kz.bootcamp.springBoot.SpringBoot.repositories.ShopItemsRepository;
import kz.bootcamp.springBoot.SpringBoot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("dbBean")
    private DatabaseBean databaseBean;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping(value="/")
    public String homepage(Model model){
        List<ShopItems> shopItems = databaseBean.getAllShopItems();
        model.addAttribute("shopItems", shopItems);
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "homepage";
    }

    @GetMapping(value="/additem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String additempage(Model model){
        List<Brands> brands = databaseBean.getAllBrands();
        model.addAttribute("brands", brands);
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "additempage";
    }

    @PostMapping(value="/additem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String toadditem(@RequestParam("itemName") String name,
                            @RequestParam("itemDesc") String description,
                            @RequestParam("itemPrice") double price,
                            @RequestParam("itemImgUrl") String imgUrl,
                            @RequestParam("itemBrand") Long brand_id){
        Brands brand = databaseBean.getBrandById(brand_id);
        if(brand != null){

            ShopItems items = new ShopItems();
            items.setName(name);
            items.setDescription(description);
            items.setPrice(price);
            items.setImg_url(imgUrl);
            items.setBrand(brand);
            databaseBean.addShopItem(items);
            return "redirect:/additem?success";
        }
        return "redirect:/additem?error";
    }

    @GetMapping(value="/details/{itemId}")
    public String detailspage(Model model, @PathVariable(name = "itemId") Long id){
        ShopItems item = databaseBean.getShopItem(id);
        model.addAttribute("shopItem", item);
        List<Brands> brands = databaseBean.getAllBrands();
        model.addAttribute("brands", brands);
        List<Categories> categories = databaseBean.getAllCategories();
        categories.removeAll(item.getCategories());
        model.addAttribute("categories", categories);
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "detailspage";
    }

    @PostMapping(value="/deleteitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String delete(@RequestParam(name = "itemId") Long id){
        databaseBean.deleteShopItem(id);
        return "redirect:/";
    }

    @PostMapping(value="/saveitem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String save(@RequestParam(name = "itemId") Long id,
                       @RequestParam(name = "itemName") String name,
                       @RequestParam(name = "itemDesc") String desc,
                       @RequestParam(name = "itemPrice") double price,
                       @RequestParam(name = "itemImgUrl") String imgUrl,
                       @RequestParam(name = "itemBrand") Long brand_id){
        ShopItems item = databaseBean.getShopItem(id);
        if(item != null) {
            Brands brand = databaseBean.getBrandById(brand_id);
            if (brand != null) {
                item.setName(name);
                item.setDescription(desc);
                item.setPrice(price);
                item.setImg_url(imgUrl);
                item.setBrand(brand);

                databaseBean.updateShopItem(item);
                return "redirect:/details/" + id;
            }
        }
        return "redirect:/detail/?error";
    }

    @PostMapping(value="/assigncategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assignCategory(@RequestParam(name="itemId") Long itemId,
                                 @RequestParam(name="categoryId") Long catId){
        Categories category = databaseBean.getCategoryById(catId);
        if(category != null) {
            ShopItems item = databaseBean.getShopItem(itemId);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if(categories == null){
                    categories = new ArrayList<>();
                }

                if(!categories.contains(category)){
                    categories.add(category);
                }

                item.setCategories(categories);
                databaseBean.updateShopItem(item);
                return "redirect:/details/" + itemId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value="/removecategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String removeCategory(@RequestParam(name="itemId") Long itemId,
                                 @RequestParam(name="categoryId") Long catId){
        Categories category = databaseBean.getCategoryById(catId);
        if(category != null) {
            ShopItems item = databaseBean.getShopItem(itemId);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if(categories == null){
                    categories = new ArrayList<>();
                }

                if(categories.contains(category)){
                    categories.remove(category);
                }

                item.setCategories(categories);
                databaseBean.updateShopItem(item);
                return "redirect:/details/" + itemId;
            }
        }
        return "redirect:/";
    }

    @GetMapping(value="/loginpage")
    public String loginpage(Model model){
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "loginpage";
    }

    @GetMapping(value="/registerpage")
    public String registerPage(Model model){
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "registerpage";
    }

    @PostMapping(value="/toregister")
    public String toRegister(@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String pass,
                             @RequestParam(name = "re_password") String repass,
                             @RequestParam(name = "full_name") String full_name){
        if(pass.length() >= 8) {
            if (pass.equals(repass)) {
                Users user = new Users();
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode(pass));
                user.setFull_name(full_name);

                if (userService.addUser(user) != null) {
                    return "redirect:/registerpage?success";
                }
                return "redirect:/registerpage?emailerror";
            }
            return "redirect:/registerpage?passworderror";
        }
        return "redirect:/registerpage?passsizeerror";
    }

    @GetMapping(value="/profilepage")
    @PreAuthorize("isAuthenticated()")
    public String profilepage(Model model){
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "profilepage";
    }

    @PostMapping(value="/updatepassword")
    @PreAuthorize("isAuthenticated()")
    public String updatePassword(@RequestParam(name="old_password") String oldPass,
                                 @RequestParam(name="new_password") String newPass,
                                 @RequestParam(name="re_password") String rePass){
        if(newPass.length() >= 8) {
            if (newPass.equals(rePass)) {
                Users user = getCurrentUser();
                if(passwordEncoder.matches(oldPass, user.getPassword())){
                    user.setPassword(passwordEncoder.encode(newPass));
                    userService.updateUser(user);
                    return "redirect:/profilepage?success";
                }
                return "redirect:/profilepage?oldpasserror";
            }
            return "redirect:/profilepage?passworderror";
        }
        return "redirect:/profilepage?passsizeerror";
    }

    @PostMapping(value="/updatename")
    @PreAuthorize("isAuthenticated()")
    public String updateName(@RequestParam(name = "full_name") String full_name){
        Users user = getCurrentUser();
        user.setFull_name(full_name);
        userService.updateUser(user);
        return "redirect:/profilepage?namesuccess";
    }

    @GetMapping(value="/accessdeniedpage")
    public String accessDeniedPage(Model model){
        model.addAttribute("CURRENT_USER", getCurrentUser());
        return "403";
    }

    private Users getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = (Users) authentication.getPrincipal();
            return user;
        }else {
            return null;
        }
    }

}