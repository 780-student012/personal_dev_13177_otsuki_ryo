package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.model.Cart;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

  
	
	
	@Autowired
	private Cart cart;
	private CustomerRepository costomerRepository;
	private OrderRepository orderRepository;
	private OrderDetailRepository orderDetailRepository;
	
	
	public OrderController(Cart cart, 
						   CustomerRepository costomerRepository,
						   OrderRepository orderRepository,
						   OrderDetailRepository orderDetailRepository) {
		super();
		this.cart = cart;
		this.costomerRepository = costomerRepository;
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
	}
	
	//顧客情報入力画面の表示
	@GetMapping("/order")
	public String index() {
		
		//カート内が空の時、直接URL入力時にカート画面にリダイレクト
		if(cart.getItems().size() == 0) {
			return "redirect:/cart";
		}
		
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
			errorList.add("名前を入力してください");
		}
		if(address.length() == 0) { //住所チェック
			errorList.add("住所を入力してください");
		}
		if(tel.length() == 0) { //電話番号チェック
			errorList.add("電話番号を入力してください");
		}
		if(email.length() == 0) { //メールアドレスチェック
			errorList.add("e-mailを入力してください");
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
	
	//注文する
	@PostMapping("/order")
	public 	String order(
						@RequestParam String name,
			  			@RequestParam String address,
			  			@RequestParam String tel,
			  			@RequestParam String email,
			  			Model model) {
		
		//お客様情報をDBに格納する
		Customer customer = new Customer(name, address, tel, email);
		costomerRepository.save(customer);
		//注文情報をDBに格納
		Order order = new Order(
				customer.getId(),
				LocalDate.now(),
				cart.getTotalPrice()) ;
		
		orderRepository.save(order);
		
		//注文詳細情報をDBに格納する
		List<Item> itemList = cart.getItems();
		List<OrderDetail> orderDetails = new ArrayList<>();
		
		for(Item item : itemList) {
			orderDetails.add(
					new OrderDetail(
						order.getId(),
						item.getId(),
						item.getQuantity()));
		}
		
		orderDetailRepository.saveAll(orderDetails);
		
		//セッションスコープのカート情報をクリア
		cart.clear();
		//画面返却用注文番号を設定する
		model.addAttribute("orderNumber", order.getId());
		
		return "G105";
	}
	
	
}
