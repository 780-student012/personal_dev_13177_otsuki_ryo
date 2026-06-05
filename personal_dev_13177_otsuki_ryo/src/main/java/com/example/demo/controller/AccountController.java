package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;

@Controller
public class AccountController {
	
	
	//フィールド
	private final HttpSession session;
	private final Account account;
	
	
	public AccountController(HttpSession session, Account account) {
		this.session = session;
		this.account = account;
	}
	
	//ログアウト処理を実行し、ログイン画面の表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		// セッション情報を全てクリアする
		session.invalidate();

		return "login";
	}

	
	//ログイン処理
	@PostMapping("/login")
	public String login(@RequestParam String name,
						Model model) {
		if (name.length() == 0) {
			model.addAttribute("message", "名前を入力してください");
			
			return "login";
		}
		//セッション管理されたアカウント情報に名前をセット
		account.setName(name);
				
		//itemsへのリダイレクト
		return "redirect:/items";
	}
}
