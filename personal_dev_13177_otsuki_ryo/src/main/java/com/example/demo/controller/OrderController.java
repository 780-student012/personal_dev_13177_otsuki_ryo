package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.model.Cart;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

    private final CategoryRepository categoryRepository;
	
	
	@Autowired
	private Cart cart;
	private CustomerRepository costomerRepository;
	private OrderRepository orderRepository;
	private OrderDetailRepository orderDetailRepository;
	
	
	public OrderController(Cart cart, 
						   CustomerRepository costomerRepository,
						   OrderRepository orderRepository,
						   OrderDetailRepository orderDetailRepository, CategoryRepository categoryRepository) {
		super();
		this.cart = cart;
		this.costomerRepository = costomerRepository;
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.categoryRepository = categoryRepository;
	}
	
	//顧客情報入力画面の表示
	@GetMapping("/order")
	public String index() {
		
		return "G103";
	}
	
	//入力内容を確認し、注文確認画面を表示
	@PostMapping("/order/confirm")
	public String confirm(
						  @RequestParam String name,
						  @RequestParam String address,
						  @RequestParam String tel,
						  @RequestParam String email,
						  Model model) {
		
		
		List<String> errorList = new ArrayList<>();
		
		if(name.length() == 0) { //名前チェック
			errorList.add("名前は必須です");
		}
		if(address.length() == 0) { //住所チェック
			errorList.add("住所は必須です");
		}
		if(tel.length() == 0) { //電話番号チェック
			errorList.add("電話番号は必須です");
		}
		if(email.length() == 0) { //メールアドレスチェック
			errorList.add("メールアドレスは必須です");
		}
		
		if(errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("name", name);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			
			//HTMLを返す
			return "G103";
		}
		
		//お客様情報をまとめる
		Customer customer = new Customer(name, address, tel, email);
		model.addAttribute("customer", customer);
		
		return "G104";
	}	
//	
//	//注文する
//	@PostMapping("/order")
//	public 	String order(
//						@RequestParam String name,
//			  			@RequestParam String address,
//			  			@RequestParam String tel,
//			  			@RequestParam String email,
//			  			Model model) {
//		
//		//お客様情報をDBに格納する
//		Customer customer = new Customer(name, address, tel, email);
//		
//		//注文情報をDBに格納
//		Order order = new Order(
//				customer.getId(),
//				LocalDate.now(),
//				cart.getTotalPrice()) ;
//		
//		orderRepository.save(order);
//		
//	}
//	
	
}
