package com.example.smart_restaurant.controller;

import com.example.smart_restaurant.model.User;
import com.example.smart_restaurant.repository.MenuItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/customer/home")
    public String customerHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("userName", user.getName());
        model.addAttribute("menuItems", menuItemRepository.findAll());
        return "customer-home";
    }
}