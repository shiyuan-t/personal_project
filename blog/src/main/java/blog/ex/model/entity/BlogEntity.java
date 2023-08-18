package blog.ex.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//@Data Lombokのアノテーションで、クラスに対してGetter、Setter、toString、equals、hashCodeメソッドを自動生成する
@Data
//@NoArgsConstructor Lombokのアノテーションで、引数なしのデフォルトコンストラクタを自動生成する
@NoArgsConstructor
//@AllArgsConstructor Lombokのアノテーションで、全ての引数をもつコンストラクタを自動生成する
@AllArgsConstructor
//@RequiredArgsConstructor Lombokのアノテーションで、全ての引数をもつコンストラクタを自動生成する
@RequiredArgsConstructor
//@Entity JPAのアノテーションで、エンティティクラスであることを示す
@Entity
//
@Table(name = "blog")

public class BlogEntity {
	// JPAのアノテーションで、プライマリーキーであることを示す
	@Id
	// @Column(name = "account_id")JPAのアノテーションで、フィールドとテーブルのカラムをマッピングする
	@Column(name = "blog_id")
	// JPAのアノテーションで、プライマリーキーを自動生成する方法を指定する
	@GeneratedValue(strategy = GenerationType.AUTO)
	// フィールド変数で、エンティティのプライマリーキーとして使用されら
	private Long blogId;
	// @NonNull Lombokのアノテーションで、nullを許容しないことを示す
	@NonNull
	@Column(name = "blog_title")
	// フィールド変数で、アカウント名を表す
	private String title;
	// フィールド変数で、メールアドレスを表す
	@NonNull
	@Column(name = "blog_image")
	private String image;
	// フィールド変数で、パスワードを表す
	@NonNull
	@Column(name = "article")
	private String article;
	// フィールド変数で、登録日時を表す
	@NonNull
	@Column(name = "register_date")
	private LocalDateTime registerDate;
	// フィールド変数で、accountIdを表す
	@Column(name = "account_id")
	private Long accountId;
	
	//パラメータから取得した値をクラスのフィールドに代入し、新しい記事エンティティを作成する
	public BlogEntity(@NonNull String title, @NonNull String image, @NonNull String article,
			@NonNull LocalDateTime registerDate, Long accountId) {
		this.title = title;
		this.image = image;
		this.article = article;
		this.registerDate = registerDate;
		this.accountId = accountId;
	}

}
