package kz.bootcamp.springBoot.SpringBoot.services;

import kz.bootcamp.springBoot.SpringBoot.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users getUser(String email);
    Users addUser(Users user);
    Users updateUser(Users user);

}
