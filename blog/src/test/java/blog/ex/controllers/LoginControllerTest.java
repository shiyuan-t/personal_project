package blog.ex.controllers;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.ex.model.entity.AccountEntity;
import blog.ex.service.AccountService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	
	@BeforeEach
	//事前のデータの準備
	public void prepareData() {
		AccountEntity accountEntity = new AccountEntity(1L,"den","den@test.com","123456", LocalDateTime.now());
		when(accountService.loginProcess(eq("den@test.com"), eq("123456"))).thenReturn(accountEntity);
		when(accountService.loginProcess(eq("dennn@test.com"), eq("123456"))).thenReturn(null);
		when(accountService.loginProcess(eq(" "), eq("123456"))).thenReturn(null);
		when(accountService.loginProcess(eq("den@test.com"), eq("123"))).thenReturn(null);
		when(accountService.loginProcess(eq(" "), eq(" "))).thenReturn(null);
		when(accountService.loginProcess(eq("dennn@test.com"), eq("123"))).thenReturn(null);
	}
	
	//ログインページを正しく取得できたかどうかのテスト
	@Test
	public void testGetLoginPage_Succees() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.get("/account/login");
		
			mockMvc.perform(request)
			.andExpect(view().name("login.html"));
	}
	
	//ログインが成功した場合
	@Test
	public void testLogin_Succees() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/account/login/process")
				.param("email","den@test.com")
				.param("password", "123456");
		
			MvcResult result = mockMvc.perform(request)
			.andExpect(redirectedUrl("/account/blog/list")).andReturn();
	}
	
	//ログインが失敗した場合、login.htmlにページが遷移する
	@Test
	public void testLogin_Fail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/account/login/process")
				.param("email","den@test.com")
				.param("password", "123456");
		
		    MvcResult result = mockMvc.perform(request)
		    .andExpect(redirectedUrl("/account/blog/list")).andReturn();
		    
		    //セッションの取得
		    HttpSession session = result.getRequest().getSession();
		    //セッションがきちんと設定出来ているかの確認テスト
		    AccountEntity accountList = (AccountEntity) session.getAttribute("account");
		    assertNull(accountList);
	}

}
