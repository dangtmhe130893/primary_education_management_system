package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Class;

@Repository
public interface ClassRepository
		extends JpaRepository<vn.edu.fptu.PrimaryEducationManagementSystem.entities.Class, Integer> {

	public List<Class> findByTeacherId(int teacherId);

	public List<Class> findBymId(int mId);

	public List<Class> findByTuId(int tuId);

	public List<Class> findByTimeId(int timeId);

}
