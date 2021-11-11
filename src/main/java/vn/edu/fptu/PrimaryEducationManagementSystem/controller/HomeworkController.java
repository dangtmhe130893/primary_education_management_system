package vn.edu.fptu.PrimaryEducationManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.edu.fptu.PrimaryEducationManagementSystem.dto.MessageResponse;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Homework;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.HomeworkRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/homework")
public class HomeworkController {

	@Autowired
	private HomeworkRepository homeworkRepository;

	@GetMapping("/teacher")
	public List<Homework> findByTeacher(@RequestParam int teacherId) {
		return homeworkRepository.findByTeacherId(teacherId);
	}

	@GetMapping("/student")
	public List<Homework> findByStudent(@RequestParam int pId) {
		return homeworkRepository.findByPupilId(pId);
	}

	@GetMapping("/sub")
	public List<Homework> findBySubId(@RequestParam int subId) {
		return homeworkRepository.findBySubId(subId);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createHomework(@RequestBody Homework homework) {

		homeworkRepository.save(homework);

		return ResponseEntity.ok(homework);
	}

	@PostMapping("/edit")
	public ResponseEntity<?> editHomework(@RequestBody Homework homework) {

		if (homeworkRepository.findById(homework.getHomeworkId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit homework"));
		}

		homeworkRepository.save(homework);
		return ResponseEntity.ok(homework);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteClass(@RequestBody Homework homework) {

		if (homeworkRepository.findById(homework.getHomeworkId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit homework"));
		}

		homeworkRepository.delete(homework);
		return ResponseEntity.ok("Delete class success !!!");
	}
}
