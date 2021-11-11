package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	/**
	 * Find account by username
	 * @param username
	 * @return Account
	 */
	Optional<Account> findByUsername(String username);
	
	/**
	 * Check exists an account by username
	 * @param username
	 * @return Boolean
	 */
	boolean existsByUsername(String username);

}
