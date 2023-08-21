package blog.ex.model.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.ex.model.entity.BlogEntity;

public interface BlogDao extends JpaRepository<BlogEntity, Long> {
	// accountIdに一致する複数のBlogEntityを取得する
	List<BlogEntity> findByAccountId(Long accountId);

	// DBに保存する
	BlogEntity save(BlogEntity blogEntity);

	// TitleとregisterDateを検索条件として、BlogEntityを取得する
	BlogEntity findByTitleAndRegisterDate(String title, LocalDateTime registerDate);

	// findBlogIdメソッド定義し、DB内のBlogEntityを検索して返す
	BlogEntity findByBlogId(Long blogId);
}
