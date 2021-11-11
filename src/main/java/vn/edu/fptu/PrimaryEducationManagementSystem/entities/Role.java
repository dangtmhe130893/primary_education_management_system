package vn.edu.fptu.PrimaryEducationManagementSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fptu.PrimaryEducationManagementSystem.common.ERole;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@Column(name = "roleid")
	private int roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "rolename")
	private ERole roleName;

	public Role() {

	}

	public Role(int roleId, ERole roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public ERole getRoleName() {
		return roleName;
	}

	public void setRoleName(ERole roleName) {
		this.roleName = roleName;
	}

}
