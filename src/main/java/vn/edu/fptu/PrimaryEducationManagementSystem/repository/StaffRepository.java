package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{

	/**
	 * Find staff By Accountid
	 * @param accId
	 * @return Staff
	 */
	Staff findByAccId(int accId);
	
}
