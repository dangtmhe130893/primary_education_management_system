package vn.edu.fptu.PrimaryEducationManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import vn.edu.fptu.PrimaryEducationManagementSystem.common.ERole;
import vn.edu.fptu.PrimaryEducationManagementSystem.controller.AuthController;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.JwtResponse;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.LoginRequest;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.SignupRequest;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Account;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Adminitrator;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Role;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.AccountRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.AdminitratorRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.HomeworkRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.service.impl.UserDetailsImpl;

@SpringBootTest
class PrimaryEducationManagementSystemApplicationTests {

	@Autowired
	private AuthController authController;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AdminitratorRepository adminitratorRepository;

	@Test
	void viewAdmin() {

		Adminitrator admin = new Adminitrator(1, "Phuong", "Lam", "Duc", "08/05/1998", "Ha Noi", "0966151921", 26, 2,
				1);

		assertEquals(1, admin.getAdId());
		assertEquals("Phuong", admin.getFirstName());
		assertEquals("Lam", admin.getLastName());
		assertEquals("Duc", admin.getMiddleName());
		assertEquals("08/05/1998", admin.getDateOfBirth());
		assertEquals("Ha Noi", admin.getAddress());
		assertEquals("0966151921", admin.getContact());
		assertEquals(26, admin.getPage());
		assertEquals(2, admin.getGender());
		assertEquals(1, admin.getAccId());
	}

	@Test
	void editAdmin() {

		Adminitrator admin = new Adminitrator(1, "Phuong", "Lam", "Duc", "08/05/1998", "Ha Noi", "0966151921", 26, 2,
				1);
		admin.setFirstName("Tuan");

		assertEquals(1, admin.getAdId());
		assertEquals("Tuan", admin.getFirstName());
		assertEquals("Lam", admin.getLastName());
		assertEquals("Duc", admin.getMiddleName());
		assertEquals("08/05/1998", admin.getDateOfBirth());
		assertEquals("Ha Noi", admin.getAddress());
		assertEquals("0966151921", admin.getContact());
		assertEquals(26, admin.getPage());
		assertEquals(2, admin.getGender());
		assertEquals(1, admin.getAccId());
	}

	@Test
	void createAdmin() {
		
		List<Adminitrator> listAdmin = new ArrayList<Adminitrator>();

		Adminitrator admin = new Adminitrator(1, "Phuong", "Lam", "Duc", "08/05/1998", "Ha Noi", "0966151921", 26, 2,
				1);
		listAdmin.add(admin);

		assertEquals(1, admin.getAdId());
		assertEquals("Phuong", admin.getFirstName());
		assertEquals("Lam", admin.getLastName());
		assertEquals("Duc", admin.getMiddleName());
		assertEquals("08/05/1998", admin.getDateOfBirth());
		assertEquals("Ha Noi", admin.getAddress());
		assertEquals("0966151921", admin.getContact());
		assertEquals(26, admin.getPage());
		assertEquals(2, admin.getGender());
		assertEquals(1, admin.getAccId());
		assertEquals(1, listAdmin.size());
		assertEquals(listAdmin.get(0), admin);
	}
	
	@Test
	void deleteAdmin() {
		
		List<Adminitrator> listAdmin = new ArrayList<Adminitrator>();

		Adminitrator admin = new Adminitrator(1, "Phuong", "Lam", "Duc", "08/05/1998", "Ha Noi", "0966151921", 26, 2,
				1);
		listAdmin.remove(admin);

		assertEquals(0, listAdmin.size());
	}
	
	@Test
	void login() {
		
		LoginRequest login = new LoginRequest("demo", "demo");
		
		assertEquals("demo", login.getUsername());
		assertEquals("demo", login.getPassword());
	}
	
	@Test
	void setRole() {
		
		Account account = new Account(1, "demo", "demo");
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(1, ERole.ROLE_ACADEMIC_HEAD));
		account.setRoles(roles);
	}
	
	@Test
	void editRole() {
		
		Account account = new Account(1, "demo", "demo");
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(1, ERole.ROLE_ACADEMIC_HEAD));
		account.setRoles(roles);
		Set<Role> roleEdit = new HashSet<Role>();
		roles.add(new Role(2, ERole.ROLE_HEADMASTER));
		account.setRoles(roleEdit);
	}

}
