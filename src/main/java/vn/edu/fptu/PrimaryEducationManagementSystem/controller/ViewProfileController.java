package vn.edu.fptu.PrimaryEducationManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fptu.PrimaryEducationManagementSystem.entities.AcademicHead;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Account;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Adminitrator;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.HeadMaster;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.PupilParent;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Staff;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Teacher;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.AcademicHeadRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.AccountRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.AdminitratorRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.HeadMasterRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.PupilParentRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.StaffRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.TeacherRepository;

@RestController
@RequestMapping("/api/profile")
public class ViewProfileController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AdminitratorRepository adminRepo;

	@Autowired
	private AcademicHeadRepository academicHeadRepository;

	@Autowired
	private HeadMasterRepository headMasterRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private PupilParentRepository pupilParentRepository;

	@GetMapping("/admin")
	public ResponseEntity<Adminitrator> findAdminProfileByAccId(@RequestParam int accId) {
		Adminitrator admin = adminRepo.findByAccId(accId);

		return new ResponseEntity<Adminitrator>(admin, HttpStatus.OK);
	}

	@PostMapping("/admin")
	public ResponseEntity<Adminitrator> editAdminProfile(@RequestBody Adminitrator admin) {

		adminRepo.save(admin);
		return new ResponseEntity<Adminitrator>(admin, HttpStatus.OK);
	}

	@GetMapping("/admin/getallaccount")
	public ResponseEntity<?> getAllAccount() {

		List<Account> listAllAccount = accountRepository.findAll();

		return new ResponseEntity<List<Account>>(listAllAccount, HttpStatus.OK);
	}

	@GetMapping("/academichead")
	public ResponseEntity<AcademicHead> findAcademichead(@RequestParam int accId) {
		AcademicHead academic = academicHeadRepository.findByAccId(accId);

		return new ResponseEntity<AcademicHead>(academic, HttpStatus.OK);
	}

	@PostMapping("/academichead")
	public ResponseEntity<AcademicHead> editAcademicProfile(@RequestBody AcademicHead academic) {

		academicHeadRepository.save(academic);
		return new ResponseEntity<AcademicHead>(academic, HttpStatus.OK);
	}

	@GetMapping("/headmaster")
	public ResponseEntity<HeadMaster> findHeadMaster(@RequestParam int accId) {
		HeadMaster headMaster = headMasterRepository.findByAccId(accId);

		return new ResponseEntity<HeadMaster>(headMaster, HttpStatus.OK);
	}

	@PostMapping("/headmaster")
	public ResponseEntity<HeadMaster> editHeadmaster(@RequestBody HeadMaster headMaster) {

		headMasterRepository.save(headMaster);
		return new ResponseEntity<HeadMaster>(headMaster, HttpStatus.OK);
	}

	@GetMapping("/pupilparent")
	public ResponseEntity<PupilParent> findPupilParent(@RequestParam int accId) {
		PupilParent pupilParent = pupilParentRepository.findByAccId(accId);

		return new ResponseEntity<PupilParent>(pupilParent, HttpStatus.OK);
	}

	@PostMapping("/pupilparent")
	public ResponseEntity<PupilParent> editPupilParent(@RequestBody PupilParent pupilParent) {

		pupilParentRepository.save(pupilParent);
		return new ResponseEntity<PupilParent>(pupilParent, HttpStatus.OK);
	}

	@GetMapping("/staff")
	public ResponseEntity<Staff> findStaff(@RequestParam int accId) {
		Staff staff = staffRepository.findByAccId(accId);

		return new ResponseEntity<Staff>(staff, HttpStatus.OK);
	}

	@PostMapping("/staff")
	public ResponseEntity<Staff> editStaff(@RequestBody Staff staff) {

		staffRepository.save(staff);
		return new ResponseEntity<Staff>(staff, HttpStatus.OK);
	}

	@GetMapping("/teacher")
	public ResponseEntity<Teacher> findTeacher(@RequestParam int accId) {
		Teacher teacher = teacherRepository.findByAccId(accId);

		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}

	@PostMapping("/teacher")
	public ResponseEntity<Teacher> editPupilParent(@RequestBody Teacher teacher) {

		teacherRepository.save(teacher);
		return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
	}

}
