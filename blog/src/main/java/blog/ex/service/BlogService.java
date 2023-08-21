package blog.ex.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import blog.ex.model.dao.BlogDao;
import blog.ex.model.entity.BlogEntity;
import lombok.NonNull;

@Service
public class BlogService {
	// blogテーブルにアクセスし操作するため、BlogDaoを使えるようにしておく
	@Autowired
	private BlogDao blogDao;

	public List<BlogEntity> findAllBlogPost(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findByAccountId(accountId);
		}
	}

	public boolean createBlogPost(String title, String image, String article, LocalDateTime registerDate,
			Long accountId) {
		// 既に同じタイトルと登録日の記事が存在するかを検索する
		BlogEntity blogList = blogDao.findByTitleAndRegisterDate(title, registerDate);
		// 存在しなければ、新しい記事を作成し保存する
		if (blogList == null) {
			blogDao.save(new BlogEntity(title, image, article, registerDate, accountId));
			return true;
			// 存在した場合
		} else {
			return false;
		}
	}

//blogIdに基づいて、blogDaoから該当するBlogEntityを取得し返す
	public BlogEntity getBlogPost(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

//ブログ情報を受け取り、指定されたblogIdに対応するブログを更新する
	public boolean editBlogPost(String title, String image, String article, Long accountId, Long blogId) {
		BlogEntity blogList = blogDao.findByBlogId(blogId);
		if (accountId == null) {
			return false;
		} else {
			blogList.setBlogId(blogId);
			blogList.setTitle(title);
			blogList.setImage(image);
			blogList.setArticle(article);
			blogList.setAccountId(accountId);
			blogDao.save(blogList);
			return true;
		}
	}
}