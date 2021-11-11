package vn.edu.fptu.PrimaryEducationManagementSystem.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fptu.PrimaryEducationManagementSystem.common.ERole;
import vn.edu.fptu.PrimaryEducationManagementSystem.common.JwtUtils;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.JwtResponse;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.LoginRequest;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.MessageResponse;
import vn.edu.fptu.PrimaryEducationManagementSystem.dto.SignupRequest;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Account;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Role;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.AccountRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.RoleRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.service.impl.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateuser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registeruser(@Validated @RequestBody SignupRequest signupRequest) {

		if (accountRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken"));
		}

		// Create new user's account
		Account account = new Account(accountRepository.findAll().size() + 1, signupRequest.getUsername(),
				encoder.encode(signupRequest.getPassword()));

		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<Role>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_GUEST)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "staff":
					Role staffRole = roleRepository.findByRoleName(ERole.ROLE_STAFF)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(staffRole);

					break;
				case "headmaster":
					Role headmasterRole = roleRepository.findByRoleName(ERole.ROLE_HEADMASTER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(headmasterRole);

					break;
				case "teacher":
					Role teacherRole = roleRepository.findByRoleName(ERole.ROLE_TEACHER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(teacherRole);

					break;
				case "pupil_parent":
					Role pupilParentRole = roleRepository.findByRoleName(ERole.ROLE_PUPIL_PARENT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(pupilParentRole);

					break;
				case "academic_head":
					Role academicHeadRoles = roleRepository.findByRoleName(ERole.ROLE_PUPIL_PARENT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(academicHeadRoles);

					break;
				default:
					Role userRole = roleRepository.findByRoleName(ERole.ROLE_GUEST)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		account.setRoles(roles);
		accountRepository.save(account);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/edit")
	public ResponseEntity<?> editUser(@Validated @RequestBody Account account) {

		if (accountRepository.findById(account.getAccId()) == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't not find account");
		}

		accountRepository.save(account);
		return ResponseEntity.ok(new MessageResponse("User edit successfully!"));
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestParam int id) {

		if (accountRepository.findById(id) == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't not find account");
		}

		accountRepository.delete(accountRepository.findById(id).get());
		return ResponseEntity.ok(new MessageResponse("User delete successfully!"));
	}

}
