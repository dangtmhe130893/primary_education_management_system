package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer>{

	public List<TimeTable> findByTeacherId(int teacherId);
	
	public List<TimeTable> findBySubId(int subId);
	
	public List<TimeTable> findByClassId(int classId);
	
}
