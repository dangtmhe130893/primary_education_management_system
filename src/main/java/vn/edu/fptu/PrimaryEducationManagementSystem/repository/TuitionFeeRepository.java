package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.TuitionFee;

@Repository
public interface TuitionFeeRepository extends JpaRepository<TuitionFee, Integer>{
	
	/**
	 * Find tuition fee by staff
	 * @param staffId
	 * @return List<TuitionFee> 
	 */
	List<TuitionFee> findByStaffId(int staffId);
	
	/**
	 * Find tuition fee by subject
	 * @param staffId
	 * @return List<TuitionFee> 
	 */
	TuitionFee findBySubId(int subId);
	
	/**
	 * Find tuition fee by headmaster
	 * @param mId
	 * @return List<TuitionFee> 
	 */
	List<TuitionFee> findBymId(int mId);
	
	/**
	 * Find tuition fee by Student
	 * @param pId
	 * @return List<TuitionFee> 
	 */
	List<TuitionFee> findBypId(int pId);
	
	/**
	 * Find tuition fee by class
	 * @param pId
	 * @return List<TuitionFee> 
	 */
	List<TuitionFee> findByClassId(int classId);
}
