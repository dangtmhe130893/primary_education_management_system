package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{

}
