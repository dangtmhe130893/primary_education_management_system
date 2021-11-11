package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Integer>{
	public List<Homework> findByPupilId(int pupilId);
	
	public List<Homework> findByTeacherId(int teacherId);
	
	public List<Homework> findBySubId(int subId);
}
