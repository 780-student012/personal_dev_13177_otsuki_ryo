package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {
	
	@Autowired
	private final Cart cart;
	private final ItemRepository itemRepository;
	
	public CartController(Cart cart, ItemRepository itemRepository) {
		this.cart = cart;
		this.itemRepository = itemRepository;
	}
	
	//カート画面の表示
	@GetMapping("/cart")
	public String index(Model model) {
		

		if(cart.getItems().size() == 0) {
			model.addAttribute("message", "カートには何も入っていません");
		}
		return "G102";
		
	}
	
	//カートに追加
	@PostMapping("/cart/add")
	public String addCart(@RequestParam int itemId,
						  @RequestParam(defaultValue = "1") Integer quantity,
						  Model model) {
		
		//カート内の個数による分岐
		for(Item item : cart.getItems()) {
			if(item.getId() == itemId) {
				if(item.getQuantity() + quantity > 10) {
					model.addAttribute("message", "カートには同一商品を10個までしか追加できません");
					List<Item> itemList = itemRepository.findAll();
					model.addAttribute("items", itemList);
					return "G101";
				}
				break;
			}
		}
		//主キーから商品情報の取得
		Item item = itemRepository.findById(itemId).get();
		// 商品オブジェクトに個数をセット
		item.setQuantity(1);
		//カートに入れる
		cart.add(item);
		
		return "redirect:/cart";
	}
	
	@PostMapping("/cart/delete")
	public String deleteCart(@RequestParam int itemId) {
		
		// カート情報から削除
		cart.delete(itemId);
		
		// 「G102」にリダイレクト
		return "redirect:/cart";
	}
}
