package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{
	
	/**
	 * Find Teacher By Accountid
	 * @param accId
	 * @return Teacher
	 */
	Teacher findByAccId(int accId);
	
}
