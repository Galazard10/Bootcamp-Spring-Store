package kz.bootcamp.springBoot.SpringBoot.services;

import kz.bootcamp.springBoot.SpringBoot.entities.Users;
import kz.bootcamp.springBoot.SpringBoot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }
}
