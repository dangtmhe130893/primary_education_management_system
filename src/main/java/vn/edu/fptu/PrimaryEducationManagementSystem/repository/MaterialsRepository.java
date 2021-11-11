package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Materials;

@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Integer>{
	
	public List<Materials> findByTeacherId(int teacherId);
	
	public List<Materials> findByClassId(int classId);
}
