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
public class CartControllerTest {
	
	
	private final MockMvc mockMvc;
	
	public CartControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
	
	@Test
	@DisplayName("カート画面が表示されること")
	void testIndex() throws Exception{
		mockMvc.perform(get("/cart"))
			.andExpect(status().isOk())
			.andExpect(view().name("G102"));
	}
	
	@Test
	@DisplayName("addCart()のPOSTリクエストにより/cartにリダイレクトされること")
	void testAddCart() throws Exception {
		mockMvc.perform(post("/cart/add").param("itemId", "1"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));
	}
	
	@Test
	@DisplayName("deleteCart()のPOSTリクエストにより/cartにリダイレクトされること")
	void testDereteCart() throws Exception {
		mockMvc.perform(post("/cart/delete").param("itemId", "1"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));
	}
}
