package blog.ex.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.ex.model.dao.AccountDao;
import blog.ex.model.entity.AccountEntity;

@Service
public class AccountService {
	// accountテーブルにアクセスし操作するため、AccountDaoを使えるようにしておく
	@Autowired
	private AccountDao accountDao;

	// アカウントを作成するためのメソッド
	public boolean createAccount(String name, String email, String password) {
		// 現在の日時を取得し、registerDateに保存する
		LocalDateTime registerDate = LocalDateTime.now();
		// 指定されたメールアドレスを持つアカウントを検索し、accountEntityに格納する
		AccountEntity accountEntity = accountDao.findByEmail(email);
		// 見つからなかった場合
		if (accountEntity == null) {
			// accountDaoのsaveメソッドを呼び出し、新しいAccountEntityオブジェクトを作成し保存する
			accountDao.save(new AccountEntity(name, email, password, registerDate));
			return true;
		} else {
			// 見つかった場合
			return false;
		}
	}

	public AccountEntity loginProcess(String email, String password) {
		// 該当するAccountEntityを検索する
		AccountEntity accountEntity = accountDao.findByEmailAndPassword(email, password);
		// 検索結果はnullであるかどうかを確認する
		if (accountEntity == null) {
			return null;
		} else {
			// ログイン成功したことを示すために検索結果のaccountEntityを返す
			return accountEntity;
		}
	}

}
