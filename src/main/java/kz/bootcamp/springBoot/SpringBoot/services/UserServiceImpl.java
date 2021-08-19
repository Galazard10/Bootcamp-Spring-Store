package kz.bootcamp.springBoot.SpringBoot.services;

import kz.bootcamp.springBoot.SpringBoot.entities.Roles;
import kz.bootcamp.springBoot.SpringBoot.entities.Users;
import kz.bootcamp.springBoot.SpringBoot.repositories.RoleRepository;
import kz.bootcamp.springBoot.SpringBoot.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users addUser(Users user) {
        Users checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser == null) {
            Roles userRole = roleRepository.findByRole("ROLE_USER");
            ArrayList<Roles> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);
            return userRepository.save(user);
        }else{
            return null;
        }

    }
}
