package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {
	
	private final Cart cart;
	private final ItemRepository itemRepository;
	
	public CartController(Cart cart, ItemRepository itemRepository) {
		this.cart = cart;
		this.itemRepository = itemRepository;
	}
	
	//カート画面の表示
	@GetMapping("/cart")
	public String index() {
		return "cart";
	}
	
	//カートに追加
	@PostMapping("/cart/add")
	public String add(@RequestParam int itemId,
					 @RequestParam Integer quantity) {
		return "cart";
	}
}
