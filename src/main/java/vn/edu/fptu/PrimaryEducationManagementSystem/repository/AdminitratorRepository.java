package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Adminitrator;

@Repository
public interface AdminitratorRepository extends JpaRepository<Adminitrator, Integer> {
	
	/**
	 * Find Admin By Accountid
	 * @param accId
	 * @return Adminitrator
	 */
	Adminitrator findByAccId(int accId);

}
