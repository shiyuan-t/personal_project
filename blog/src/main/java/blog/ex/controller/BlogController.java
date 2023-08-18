package blog.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.ex.model.entity.AccountEntity;
import blog.ex.model.entity.BlogEntity;
import blog.ex.service.BlogService;
import jakarta.servlet.http.HttpSession;

//全てのエンドポイントは/account/blogで始まる
@RequestMapping("/account/blog")

@Controller
public class BlogController {
	//BlogServiceクラスのメソッドを呼び出し、指定した処理を実行する
	@Autowired
	private BlogService blogService;
	//ユーザーごとに状態を保持する
	@Autowired
	private HttpSession session;

	//受け取るメソッド（ブログ画面表示）
	@GetMapping("/list")
	public String getBlogListPage(Model model) {
		//現在のユーザー情報を取得する
		AccountEntity accountList = (AccountEntity) session.getAttribute("account");
		Long accountId = accountList.getAccountId();
		//accountListから現在ログインしているユーザー名を取得する
		String name = accountList.getName();
		//blogServiceのfindAllBlogPostを呼び出し、現在のユーザーに関連するブログ投稿を取得する
		List<BlogEntity> blogList = blogService.findAllBlogPost(accountId);
		//ModelクラスにnameとblogListを追加し、blog.htmlというビューを返しいる
		model.addAttribute("name", name);
		model.addAttribute("blogList", blogList);
		return "blog.html";
	}
	
	//受け取るメソッド（ブログ登録画面表示）
    @GetMapping("/register")
    public String getBlogRegisterPage(Model model) {
    	AccountEntity accountList = (AccountEntity) session.getAttribute("account");
		Long accountId = accountList.getAccountId();
		//accountListから現在ログインしているユーザー名を取得する
		String name = accountList.getName();
		//blogServiceのfindAllBlogPostを呼び出し、現在のユーザーに関連するブログ投稿を取得する
		List<BlogEntity> blogList = blogService.findAllBlogPost(accountId);
		//ModelクラスにnameとblogListを追加し、blog_register.htmlというビューを返しいる
		model.addAttribute("name", name);
		model.addAttribute("register", "register");
		return "blog_register.html";
    }
    //
    @PostMapping("/register/process")
    public String blogRegister(@RequestParam String title,
    		@RequestParam MultipartFile image,
    		@RequestParam String article,
    		Model model) {
    	//現在のユーザー情報を取得する
    		AccountEntity accountList = (AccountEntity) session.getAttribute("account");
    		Long accountId = accountList.getAccountId();
    	/*現在の日時情報を元に、ファイル名を作成している
    	 *その後、imageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結してfileName変数に代入する*/
    		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())+image.getOriginalFilename();
    		try {
    			/*ファイルを実際にサーバー上に保存処理する
    			 *Files.copy()を使用し、imageオブジェクトから入力ストリームを取得して指定されたパスにコピーする*/
    			Files.copy(image.getInputStream(), Path.of("src/main/resources/static/blog-img/"+fileName));
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    		//現在の日時を取得する
    		LocalDateTime registerDate = LocalDateTime.now();
    		//blogService.createBlogPostを呼び出し、データをDBに保存する
    		if(blogService.createBlogPost(title, fileName, article, registerDate, accountId)) {
    			return "redirect:/account/blog/list";
    		}else {
    			model.addAttribute("registerMessage","既に登録済みです");
    			return "blog_register.html";
    		}
    }
}
    
    
