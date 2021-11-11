package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.AcademicHead;

@Repository
public interface AcademicHeadRepository extends JpaRepository<AcademicHead, Integer> {
	
	/**
	 * Find Academichead By Accountid
	 * @param accId
	 * @return AcademicHead
	 */
	AcademicHead findByAccId(int accId);
	
}
