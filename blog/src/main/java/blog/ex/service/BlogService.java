package blog.ex.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.ex.model.dao.BlogDao;
import blog.ex.model.entity.BlogEntity;

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

public boolean createBlogPost(String title,String image,String article,LocalDateTime registerDate,Long accountId) {
	//既に同じタイトルと登録日の記事が存在するかを検索する
	BlogEntity blogEntity = blogDao.findByTitleAndRegisterDate(title, registerDate);
	//存在しなければ、新しい記事を作成し保存する
	if(blogEntity==null) {
		blogDao.save(new BlogEntity(title,image,article,registerDate,accountId));
		return true;
	//存在した場合
	}else {
		return false;
	}

}
}