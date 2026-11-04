package com.example.smart_restaurant.config;

import com.example.smart_restaurant.model.MenuItem;
import com.example.smart_restaurant.model.User;
import com.example.smart_restaurant.repository.MenuItemRepository;
import com.example.smart_restaurant.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(MenuItemRepository menuRepo, UserRepository userRepo) {
        return args -> {
            // Default Admin user (if not exists)
            if (userRepo.findByEmail("admin@restaurant.com").isEmpty()) {
                userRepo.save(new User("Admin Chef", "admin@restaurant.com", "admin123", "ROLE_ADMIN"));
            }

            // Sample Menu Items
            if (menuRepo.count() == 0) {
                menuRepo.deleteAll();
                menuRepo.save(new MenuItem(
                    "Truffle Mushroom Pasta", 
                    "Creamy fettuccine with wild forest mushrooms and aromatic white truffle oil.", 
                    340.0, 
                    "Main Course", 
                    "https://images.unsplash.com/photo-1621996346565-e3d5d6281691?auto=format&fit=crop&w=800&q=80"
                ));
                menuRepo.save(new MenuItem(
                    "Artisan Wood-Fired Margherita", 
                    "San Marzano tomatoes, buffalo mozzarella, and fresh garden basil.", 
                    290.0, 
                    "Main Course", 
                    "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?w=500"
                ));
                menuRepo.save(new MenuItem(
                    "Crispy Herb Calamari", 
                    "Lightly seasoned crisp rings served with roasted garlic aioli dip.", 
                    220.0, 
                    "Starters", 
                    "https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?auto=format&fit=crop&w=800&q=80"
                ));
                menuRepo.save(new MenuItem(
                    "Classic Iced Berry Mojito", 
                    "Crushed fresh mint, wild berries, zesty lime, and chilled sparkling soda.", 
                    140.0, 
                    "Beverages", 
                    "https://images.unsplash.com/photo-1551024709-8f23befc6f87?w=500"
                ));
                menuRepo.save(new MenuItem(
                    "Warm Molten Choco Lava Cake", 
                    "Rich Belgian dark chocolate heart with a scoop of Madagascar vanilla gelato.", 
                    190.0, 
                    "Desserts", 
                    "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=500"
                ));
            }
        };
    }
}
