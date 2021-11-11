package vn.edu.fptu.PrimaryEducationManagementSystem.controller;

import java.util.List;

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
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Materials;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.MaterialsRepository;

@RestController
@RequestMapping("/api/materials")
public class MaterialsController {
	
	@Autowired
	private MaterialsRepository materialsRepository;

	@GetMapping("/teacher")
	public ResponseEntity<?> findByTeacher(@RequestParam int teacherId) {
		return ResponseEntity.ok(materialsRepository.findByTeacherId(teacherId));	
	}
	
	@GetMapping("/class")
	public ResponseEntity<?> findByClassId(@RequestParam int classId) {
		return ResponseEntity.ok(materialsRepository.findByClassId(classId));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createMaterials(@RequestBody Materials materials) {
		materialsRepository.save(materials);
		
		return ResponseEntity.ok(materials);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editMaterial(@RequestBody Materials materials) {
		
		if(materialsRepository.findById(materials.getMasterId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not edit material"));
		}
		
		materialsRepository.save(materials);
		return ResponseEntity.ok(materials);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteClass(
			@Validated @RequestBody Materials materials) {

		if (materialsRepository.findById(materials.getMasterId()) == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: can not delete material"));
		}

		materialsRepository.delete(materials);
		return ResponseEntity.ok("Delete class success !!!");
	}
	
}
