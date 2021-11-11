package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.HeadMaster;

@Repository
public interface HeadMasterRepository extends JpaRepository<HeadMaster, Integer>{

	/**
	 * Find Headmaster By Accountid
	 * @param accId
	 * @return Headmaster
	 */
	HeadMaster findByAccId(int accId);
	
}
