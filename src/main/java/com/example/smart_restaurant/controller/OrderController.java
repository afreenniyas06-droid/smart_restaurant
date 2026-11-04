package com.example.smart_restaurant.controller;

import com.example.smart_restaurant.model.MenuItem;
import com.example.smart_restaurant.model.Order;
import com.example.smart_restaurant.model.OrderItem;
import com.example.smart_restaurant.model.User;
import com.example.smart_restaurant.repository.MenuItemRepository;
import com.example.smart_restaurant.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public static class OrderRequest {
        public Integer tableNumber;
        public List<CartItemRequest> items;
    }

    public static class CartItemRequest {
        public Long id;
        public Integer quantity;
    }

    @PostMapping("/api/orders/place")
    @ResponseBody
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest payload, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User not logged in"));
        }

        if (payload.items == null || payload.items.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Cart is empty"));
        }

        Order order = new Order();
        order.setCustomer(user);
        order.setTableNumber(payload.tableNumber != null ? payload.tableNumber : 1);

        double total = 0.0;
        for (CartItemRequest reqItem : payload.items) {
            MenuItem mi = menuItemRepository.findById(reqItem.id).orElse(null);
            if (mi != null) {
                OrderItem item = new OrderItem(mi, reqItem.quantity, mi.getPrice() * reqItem.quantity);
                order.getItems().add(item);
                total += mi.getPrice() * reqItem.quantity;
            }
        }
        order.setTotalAmount(total);
        orderRepository.save(order);

        return ResponseEntity.ok(Map.of("orderId", order.getId(), "message", "Order placed successfully!"));
    }

    @GetMapping("/customer/orders")
    public String viewMyOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("userName", user.getName());
        model.addAttribute("orders", orderRepository.findByCustomerOrderByOrderTimeDesc(user));
        return "customer-orders";
    }
}