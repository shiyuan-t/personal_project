package blog.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.ex.model.entity.AccountEntity;
import blog.ex.service.AccountService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/account")

@Controller
public class AccountLoginController {
	// 自動的にAccountServiceを注入し呼び出す
	@Autowired
	private AccountService accountService;
	@Autowired
	private HttpSession session;

	@GetMapping("/login")
	public String getAccountLoginPage() {
		return "login.html";
	}

	@PostMapping("/login/process")
	public String login(@RequestParam String email, @RequestParam String password) {
		// accountServiceのloginProcessメソッドを呼び出し、指定された◯◯でログインできるかどうかを確認する
		AccountEntity accountList = accountService.loginProcess(email, password);
		if (accountList == null) {
			return "redirect:/account/login";
		} else {
			session.setAttribute("account", accountList);
			return "redirect:/account/blog/list";
		}
	}

}
