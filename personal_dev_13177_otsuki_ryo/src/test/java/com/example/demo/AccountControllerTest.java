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
public class AccountControllerTest {

		private final MockMvc mockMvc;
		
		public AccountControllerTest(MockMvc mockMvc) {
			this.mockMvc = mockMvc;
		}
		
		@Test
		@DisplayName("URLパス/時にG001を返すこと")
		void testIndex1() throws Exception{
			mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("G001"));
		}
		
		@Test
		@DisplayName("URLパス/login時にG001を返すこと")
		void testIndex2() throws Exception{
			mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("G001"));
		}
		
		@Test
		@DisplayName("URLパス/logout時にG001を返すこと")
		void testIndex3() throws Exception{
			mockMvc.perform(get("/logout"))
			.andExpect(status().isOk())
			.andExpect(view().name("G001"));
		}
		
		@Test
		@DisplayName("正しくログインができること")
		void testLogin()throws Exception{
			mockMvc.perform(post("/login").param("name", "田中太郎"))
					.andExpect(status().isFound())
					.andExpect(redirectedUrl("/items"));
		}
		
		@Test
		@DisplayName("nameが入力されていない際にエラーメッセージと共にG001が返されること")
		void testLoginNoName() throws Exception {
			mockMvc.perform(post("/login").param("name", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("G001"))
			.andExpect(model().attribute("message", "名前を入力してください"));
		}
		
		
}
