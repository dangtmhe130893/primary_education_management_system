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
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.TimeTable;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.TimeTableRepository;

@RestController
@RequestMapping("/api/timetable")
public class TimeTableController {

	@Autowired
	private TimeTableRepository timeTableRepository;
	
	@GetMapping("/sub")
	public ResponseEntity<?> findTimetableBySub(@RequestParam int subId) {
		
		return ResponseEntity.ok(timeTableRepository.findBySubId(subId));
	}
	
	@GetMapping("/teacher")
	public ResponseEntity<?> findByTeacher(@RequestParam int teacherId) {
		
		return ResponseEntity.ok(timeTableRepository.findByTeacherId(teacherId));
	}
	
	@GetMapping("/class")
	public ResponseEntity<?> findByClass(@RequestParam int classId) {
		
		return ResponseEntity.ok(timeTableRepository.findByClassId(classId));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createTimeTable(@Validated @RequestBody TimeTable timeTable) {
		timeTableRepository.save(timeTable);

		return ResponseEntity.ok(timeTable);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editTuitionFee(@Validated @RequestBody TimeTable timeTable) {

		if (timeTableRepository.findById(timeTable.getTimeId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit time table"));
		}

		timeTableRepository.save(timeTable);
		return ResponseEntity.ok(timeTable);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteTimeTable(@Validated @RequestBody TimeTable timeTable) {

		if (timeTableRepository.findById(timeTable.getTimeId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit time table"));
		}

		timeTableRepository.delete(timeTable);
		return ResponseEntity.ok("Delete time table success !!!");
	}
}
