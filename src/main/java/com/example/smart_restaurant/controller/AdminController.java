package com.example.smart_restaurant.controller;

import com.example.smart_restaurant.model.Order;
import com.example.smart_restaurant.model.User;
import com.example.smart_restaurant.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ROLE_ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("adminName", user.getName());
        model.addAttribute("orders", orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderTime")));
        return "admin-dashboard";
    }

    @PostMapping("/orders/status")
    public String updateStatus(@RequestParam Long orderId, @RequestParam String status, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ROLE_ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
        return "redirect:/admin/dashboard";
    }
}