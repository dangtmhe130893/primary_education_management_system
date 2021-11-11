package vn.edu.fptu.PrimaryEducationManagementSystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fptu.PrimaryEducationManagementSystem.dto.TuitionFeeResponse;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.Subject;
import vn.edu.fptu.PrimaryEducationManagementSystem.entities.TuitionFee;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.SubjectRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.repository.TuitionFeeRepository;
import vn.edu.fptu.PrimaryEducationManagementSystem.service.TuitionFeeService;

@Service
public class TuitionFeeServiceImpl implements TuitionFeeService{
	
	@Autowired
	TuitionFeeRepository tuitionFeeRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Override
	public List<TuitionFeeResponse> findAllTuitionFee() {
		List<TuitionFeeResponse> tuitionFeeResponses = new ArrayList<TuitionFeeResponse>();
		
		List<TuitionFee> tuitionFee = tuitionFeeRepository.findAll();
		
		tuitionFee.forEach(t -> {
			
			Subject subject = subjectRepository.findById(t.getSubId()).get();
			
			TuitionFeeResponse tuitionFeeResponse = new TuitionFeeResponse();
			tuitionFeeResponse.setTuId(t.getTuId());
			tuitionFeeResponse.setSubId(t.getSubId());
			tuitionFeeResponse.setSubName(subject.getSubName());
			tuitionFeeResponse.setPrice(t.getPrice());
			tuitionFeeResponse.setDate(t.getDate());
		});
		
		return tuitionFeeResponses;
	}
	
}
