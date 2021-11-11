package vn.edu.fptu.PrimaryEducationManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.fptu.PrimaryEducationManagementSystem.dto.MessageResponse;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.TuitionFee;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.TuitionFeeRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.service.TuitionFeeService;

@RestController
@RequestMapping("/api/tuition")
public class TuitionFeeController {

	@Autowired
	TuitionFeeRepository tuitionFeeRepo;

	@Autowired
	TuitionFeeService tuitionFeeService;

	@GetMapping
	public ResponseEntity<?> findAll() {

		return ResponseEntity.ok(tuitionFeeService.findAllTuitionFee());
	}

	@PostMapping("/create")
	public ResponseEntity<?> createTuitionFee(@Validated @RequestBody TuitionFee tuitionFee) {
		tuitionFeeRepo.save(tuitionFee);

		return ResponseEntity.ok(tuitionFee);
	}

	@PostMapping("/edit")
	public ResponseEntity<?> editTuitionFee(@Validated @RequestBody TuitionFee tuitionFee) {

		if (tuitionFeeRepo.findById(tuitionFee.getTuId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit tuition fee"));
		}

		tuitionFeeRepo.save(tuitionFee);
		return ResponseEntity.ok(tuitionFee);
	}

	@GetMapping("/find/student")
	public ResponseEntity<?> findTutionFeeByStudentId(@RequestParam int pId) {

		return ResponseEntity.ok(tuitionFeeRepo.findBypId(pId));
	}

	@GetMapping("/find/headmaster")
	public ResponseEntity<?> findTutionFeeByHeadmasterid(@RequestParam int mId) {

		return ResponseEntity.ok(tuitionFeeRepo.findBymId(mId));
	}

	@GetMapping("/find/staff")
	public ResponseEntity<?> findTutionFeeByStaffId(@RequestParam int sId) {

		return ResponseEntity.ok(tuitionFeeRepo.findByStaffId(sId));
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteTuitionFee(@Validated @RequestBody TuitionFee tuitionFee) {

		if (tuitionFeeRepo.findById(tuitionFee.getTuId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit tuition fee"));
		}

		tuitionFeeRepo.delete(tuitionFee);
		return ResponseEntity.ok("Delete tuition fee success !!!");
	}
}
