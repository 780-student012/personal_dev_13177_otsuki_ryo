package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class OrderControllerTest {
	
	
	private final MockMvc mockMvc;
	
	public OrderControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
//	
//	@Test
//	@DisplayName("顧客情報画面が表示されること")
//	void testIndex() throws Exception{
//		//仮のカートを作ってリダイレクトしないように
//		
//		Cart cart = new Cart();
//		cart.add(new Item(1, "本", 1000, 1));
//	   
//
//	    MockHttpSession session = new MockHttpSession();
//	    session.setAttribute("cart", cart);
//		
//		mockMvc.perform(get("/order")
//			.session(session))
//			.andExpect(status().isOk())
//			.andExpect(view().name("G103"));
//	}
	
	
	@Test
	@DisplayName("confirm()のPOSTリクエストが処理によりG104が返されること")
	void testConfirm() throws Exception {
		mockMvc.perform(post("/order/confirm").param("name", "田中太郎")
											  .param("address", "東京都")
											  .param("tel", "090-1111-1111")
											  .param("email", "tanaka@aaa.cm"))
		.andExpect(status().isOk())
		.andExpect(view().name("G104"));
	}
	
	@Test
	@DisplayName("confirm()のPOSTリクエストにて入力がない際にG103が返されること")
	void testConfirmNoParam() throws Exception {
		mockMvc.perform(post("/order/confirm").param("name", "")
											  .param("address", "")
											  .param("tel", "")
											  .param("email", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("G103"));
	}
	
	@Test
	@DisplayName("order()のPOSTリクエストが処理によりG105が返されること")
	void testOrder() throws Exception {
		mockMvc.perform(post("/order").param("name", "田中太郎")
									   .param("address", "東京都")
									   .param("tel", "090-1111-1111")
									   .param("email", "tanaka@aaa.cm"))
		.andExpect(status().isOk())
		.andExpect(view().name("G105"));
	}
}
