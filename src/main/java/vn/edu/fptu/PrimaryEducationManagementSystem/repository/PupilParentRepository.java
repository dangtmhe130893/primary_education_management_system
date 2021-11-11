package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.PupilParent;

@Repository
public interface PupilParentRepository extends JpaRepository<PupilParent, Integer>{
	
	/**
	 * Find pupil parent By Accountid
	 * @param accId
	 * @return PupilParent
	 */
	PupilParent findByAccId(int accId);
	
}
