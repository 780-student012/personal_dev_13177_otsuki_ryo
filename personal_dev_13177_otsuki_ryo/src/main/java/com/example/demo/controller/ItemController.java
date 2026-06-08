package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {
	
	@Autowired
	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;
	
	public ItemController(CategoryRepository categoryRepository, ItemRepository itemRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.itemRepository = itemRepository;
	 }
	
	//商品一覧画面の表示
	@GetMapping("/items")
	public String index(@RequestParam(defaultValue = "") Integer categoryId,
						Model model) {
		
		//カテゴリー一覧情報の取得
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		
		//商品情報の取得
		List<Item> itemList = null;
		//カテゴリー選択時の表示
		if(categoryId != null) {  //カテゴリーで絞る
			itemList = itemRepository.findByCategoryId(categoryId);
		}else {  //何もない場合
			itemList = itemRepository.findAll();
		}
		
		if(itemList.size() == 0) {
			model.addAttribute("categoryMessage", "該当する商品がありません");
		}
		
		model.addAttribute("items", itemList);

		return "G101";
	}

}
