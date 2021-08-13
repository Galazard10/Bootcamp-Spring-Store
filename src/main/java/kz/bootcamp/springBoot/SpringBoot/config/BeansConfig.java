package kz.bootcamp.springBoot.SpringBoot.config;

import kz.bootcamp.springBoot.SpringBoot.beans.DatabaseBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean(name="dbBean")
    public DatabaseBean databaseBean(){
        return new DatabaseBean();
    }

}
