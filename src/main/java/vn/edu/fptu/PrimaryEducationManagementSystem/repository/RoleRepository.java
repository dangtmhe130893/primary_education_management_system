package vn.edu.fptu.PrimaryEducationManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.fptu.PrimaryEducationManagementSystem.common.ERole;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	/**
	 * Find role by name
	 * @param roleName
	 * @return Role
	 */
	Optional<Role> findByRoleName(ERole roleName);
	
}
