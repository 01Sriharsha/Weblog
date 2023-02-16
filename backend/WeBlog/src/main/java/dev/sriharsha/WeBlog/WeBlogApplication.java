package dev.sriharsha.WeBlog;

import dev.sriharsha.WeBlog.constant.AppConstant;
import dev.sriharsha.WeBlog.dto.CommentDto;
import dev.sriharsha.WeBlog.entity.Role;
import dev.sriharsha.WeBlog.repository.RoleRepository;
import dev.sriharsha.WeBlog.service.CommentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@SpringBootApplication
public class WeBlogApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(WeBlogApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {


        //Generating 2 roles by default on the start of the application
        Role role = new Role();
        role.setId(AppConstant.NORMAL_USER);
        role.setName("ROLE_USER");

        Role role1 = new Role();
        role1.setId(AppConstant.ADMIN_USER);
        role1.setName("ROLE_ADMIN");

        List<Role> roles = List.of(role, role1);

        roleRepository.saveAllAndFlush(roles);
    }

    //Cors Configuration
    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            public void addCorsMapping(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOriginPatterns("*")
                        .allowedHeaders("*")
                        .allowedHeaders("Authorization")
                        .allowedHeaders("Content-Type")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:3000");
            }

        };
    }
}
