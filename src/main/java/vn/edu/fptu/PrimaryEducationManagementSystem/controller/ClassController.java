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
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.ClassRepository;

@RestController
@RequestMapping("/api/class")
public class ClassController {

	@Autowired
	ClassRepository classRepo;

	@PostMapping("/create")
	public ResponseEntity<?> createClass(
			@Validated @RequestBody vn.edu.fptu.PrimaryEducationManagementSystem.entities.Class studentClass) {
		classRepo.save(studentClass);

		return ResponseEntity.ok(studentClass);
	}

	@PostMapping("/edit")
	public ResponseEntity<?> editClass(
			@Validated @RequestBody vn.edu.fptu.PrimaryEducationManagementSystem.entities.Class studentClass) {

		if (classRepo.findById(studentClass.getClassId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit class"));
		}

		classRepo.save(studentClass);
		return ResponseEntity.ok(studentClass);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteClass(
			@Validated @RequestBody vn.edu.fptu.PrimaryEducationManagementSystem.entities.Class studentClass) {

		if (classRepo.findById(studentClass.getClassId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not delete class"));
		}

		classRepo.delete(studentClass);
		return ResponseEntity.ok("Delete class success !!!");
	}
	
	@GetMapping("/find/teacher")
	public ResponseEntity<?> findTutionFeeByTeacherId(@RequestParam int teacherId) {

		return ResponseEntity.ok(classRepo.findByTeacherId(teacherId));
	}
	
	@GetMapping("/find/tution")
	public ResponseEntity<?> findClassByTutionFee(@RequestParam int tuId) {

		return ResponseEntity.ok(classRepo.findByTuId(tuId));
	}

}
