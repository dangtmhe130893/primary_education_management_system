package vn.edu.fptu.PrimaryEducationManagementSystem.service;

import java.util.List;

import vn.edu.fptu.PrimaryEducationManagementSystem.dto.TuitionFeeResponse;

public interface TuitionFeeService {

	List<TuitionFeeResponse> findAllTuitionFee();

}
