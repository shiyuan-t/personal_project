package blog.ex.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.ex.model.entity.AccountEntity;

//JpaRepository（Spring Data JPAが提供）を継承し、エンティティのCRUD操作を実装する
public interface AccountDao extends JpaRepository<AccountEntity, Long> {
	// AccountEntityを引数として受け取り、AccountEntityを保存し、保存したAccountEntityを返す
	AccountEntity save(AccountEntity accountEntity);

	// String型の引数を受け取り、その引数を一致するaccountEmailを持つAccountEntityを返す
	AccountEntity findByEmail(String email);

	// 一致するものを検索し返す
	AccountEntity findByEmailAndPassword(String email, String password);
}
