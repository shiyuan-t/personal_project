package blog.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.ex.service.AccountService;

// HTTPリクエストに対するマッピング
@RequestMapping("/account")

@Controller
public class AccountRegitserController {
	// AccountServiceクラスのメソッドを呼び出し、新規登録の処理を実行する
	@Autowired
	private AccountService accountService;

	// HTTP GETリクエストに対するマッピング
	@GetMapping("/register")
	public String getAccountRegisterPage() {
		return "register.html";
	}

	// HTTP POSTリクエストに対するマッピング
	@PostMapping("/register/process")
	public String register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
		if (accountService.createAccount(name, email, password)) {
			return "redirect:/account/login";
		} else {
			return "register.html";
		}

	}

}
