package com.primary_education_system.service;

import com.google.common.collect.Lists;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.history_pay_tuition.PupilIdAndTuitionSubmitted;
import com.primary_education_system.dto.history_pay_tuition.PupilTuitionDto;
import com.primary_education_system.dto.history_pay_tuition.RevenueTuitionDto;
import com.primary_education_system.dto.history_pay_tuition.UpdateTuitionRequestDto;

import com.primary_education_system.dto.pupil_account.HistoryPayTuitionDto;
import com.primary_education_system.entity.pupil.HistoryPayTuitionEntity;
import com.primary_education_system.entity.pupil.PupilAccountEntity;
import com.primary_education_system.repository.HistoryPayTuitionRepository;
import com.primary_education_system.util.Constant;
import com.primary_education_system.repository.TuitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HistoryPayTuitionService {

    @Autowired
    private HistoryPayTuitionRepository historyPayTuitionRepository;

    @Autowired
    private PupilAccountService pupilAccountService;

    @Autowired
    private TuitionService tuitionService;

    @Autowired
    private ClassService classService;

    @Autowired
    private TuitionRepository tuitionRepository;

    public ServerResponseDto findByPupilId(Long pupilId) {
        PupilAccountEntity pupil = pupilAccountService.findById(pupilId);
        if (pupil == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        HistoryPayTuitionDto result = new HistoryPayTuitionDto();
        List<HistoryPayTuitionEntity> history = historyPayTuitionRepository.findByPupilId(pupilId);
        result.setHistory(history);
        result.setTotal(tuitionRepository.findTop1ByGradeAndIsDeletedFalse(pupil.getGrade()).getFee());
        return new ServerResponseDto(ResponseCase.SUCCESS, result);
    }

    public Page<RevenueTuitionDto> getPage(String type, Long classIdRequest, String keyword, Pageable pageable) {
        Page<RevenueTuitionDto> result;
        if ("all_school".equals(type)) {
            result = new PageImpl<>(Arrays.asList(getRevenueForAllSchool()), pageable, 1);
        } else if ("group_by_grade".equals(type)) {
            Map<String, Integer> mapNumberPupilByGrade = pupilAccountService.getMapNumberPupilByGrade();
            Map<String, Integer> mapTuitionByGrade = tuitionService.getMapTuitionByGrade();
            Map<String, Integer> mapTotalTuitionReceivedByGrade = getMapTotalTuitionReceivedByGrade();

            List<RevenueTuitionDto> listRevenueTuition = Lists.newArrayListWithCapacity(5);
            Constant.setGrade.forEach(grade -> {
                RevenueTuitionDto revenueTuitionDto = new RevenueTuitionDto();
                revenueTuitionDto.setGrade(grade);
                revenueTuitionDto.setNumberPupil(mapNumberPupilByGrade.getOrDefault(grade, 0));
                revenueTuitionDto.setTuitionRequire(mapTuitionByGrade.getOrDefault(grade, 0) * revenueTuitionDto.getNumberPupil());
                revenueTuitionDto.setTuitionReceived(mapTotalTuitionReceivedByGrade.get(grade));
                revenueTuitionDto.setDoneTuition(revenueTuitionDto.getTuitionRequire().equals(revenueTuitionDto.getTuitionReceived()));

                listRevenueTuition.add(revenueTuitionDto);
            });
            result = new PageImpl<>(listRevenueTuition, pageable, Constant.setGrade.size());
        } else if ("group_by_class".equals(type)) {
            List<Long> listClassId = classService.getALlClassId();
            Map<Long, Integer> mapNumberPupilByClassId = pupilAccountService.getMapNumberPupilByClassId(listClassId);
            Map<Long, Integer> mapTuitionByClassId = tuitionService.getMapTuitionByClassId(listClassId);
            Map<Long, Integer> mapTotalTuitionReceivedByClassId = getMapTotalTuitionReceivedByClassId(listClassId);
            Map<Long, String> mapClassNameByClassId = classService.getMapClassNameByClassId(listClassId);

            List<RevenueTuitionDto> listRevenueTuition = Lists.newArrayListWithCapacity(listClassId.size());
            listClassId.forEach(classId -> {
                RevenueTuitionDto revenueTuitionDto = new RevenueTuitionDto();
                revenueTuitionDto.setClassName(mapClassNameByClassId.get(classId));
                revenueTuitionDto.setNumberPupil(mapNumberPupilByClassId.getOrDefault(classId, 0));
                revenueTuitionDto.setTuitionRequire(mapTuitionByClassId.getOrDefault(classId, 0) * revenueTuitionDto.getNumberPupil());
                revenueTuitionDto.setTuitionReceived(mapTotalTuitionReceivedByClassId.getOrDefault(classId, 0));
                revenueTuitionDto.setDoneTuition(revenueTuitionDto.getTuitionRequire().equals(revenueTuitionDto.getTuitionReceived()));

                listRevenueTuition.add(revenueTuitionDto);
            });
            result = new PageImpl<>(listRevenueTuition, pageable, listClassId.size());
        } else if ("all_pupil".equals(type)) {
            List<PupilAccountEntity> listPupil;
            if (classIdRequest == 0) {
                if (keyword == null) keyword = "";
                listPupil = pupilAccountService.findByKeywordAndByIsDeletedFalse(keyword);
            } else {
                listPupil = pupilAccountService.getByClassId(classIdRequest);
            }

            List<Long> listPupilId = listPupil
                    .stream()
                    .map(PupilAccountEntity::getId)
                    .collect(Collectors.toList());

            Map<Long, PupilAccountEntity> mapPupilByPupilId = listPupil
                    .stream()
                    .collect(Collectors.toMap(PupilAccountEntity::getId, Function.identity()));
            Map<Long, Integer> mapTuitionPupilByPupilId = tuitionService.getMapTuitionPupilByPupilId(listPupil);
            Map<Long, Integer> mapTuitionSubmittedByClassId = getMapTuitionSubmittedByClassId(listPupilId);

            List<RevenueTuitionDto> listRevenueTuition = Lists.newArrayListWithCapacity(listPupilId.size());
            listPupilId.forEach(pupilId -> {
                RevenueTuitionDto revenueTuitionDto = new RevenueTuitionDto();
                revenueTuitionDto.setPupilId(pupilId);
                revenueTuitionDto.setCodePupil(mapPupilByPupilId.get(pupilId).getCode());
                revenueTuitionDto.setPupilName(mapPupilByPupilId.get(pupilId).getName());
                revenueTuitionDto.setTuitionRequire(mapTuitionPupilByPupilId.getOrDefault(pupilId, 0));
                revenueTuitionDto.setTuitionReceived(mapTuitionSubmittedByClassId.getOrDefault(pupilId, 0));
                revenueTuitionDto.setDoneTuition(revenueTuitionDto.getTuitionRequire().equals(revenueTuitionDto.getTuitionReceived()));

                listRevenueTuition.add(revenueTuitionDto);
            });
            result = new PageImpl<>(listRevenueTuition, pageable, listPupilId.size());
        } else {
            result = new PageImpl<>(Collections.emptyList());
        }
        return result;
    }

    private Map<Long, Integer> getMapTuitionSubmittedByClassId(List<Long> listPupilId) {
        if (listPupilId.isEmpty()) {
            return Collections.emptyMap();
        }
        List<PupilIdAndTuitionSubmitted> listObjectPupilIdAndTuitionSubmitted = historyPayTuitionRepository
                .getListObjectTuitionIdAndTotalTuitionSubmitted(listPupilId);

        Map<Long, Integer> mapResult = new HashMap<>(listObjectPupilIdAndTuitionSubmitted.size());
        listObjectPupilIdAndTuitionSubmitted.forEach(object -> {
            mapResult.put(object.getPupilId(), object.getTuitionSubmitted());
        });
        return mapResult;
    }

    private Map<Long, Integer> getMapTotalTuitionReceivedByClassId(List<Long> listClassId) {
        Map<Long, Integer> mapTotalTuitionSubmittedByPupilId = getMapTotalTuitionSubmittedByPupil();

        Set<Long> listTuitionId = mapTotalTuitionSubmittedByPupilId.keySet();
        Map<Long, List<Long>> mapListPupilIdByClassId = pupilAccountService.getMapListPupilIdByClassId(listTuitionId);

        Map<Long, Integer> mapResult = new HashMap<>(listClassId.size());
        listClassId.forEach(classId -> {
            List<Long> listPupilId = mapListPupilIdByClassId.get(classId);
            if (listPupilId == null) {
                mapResult.put(classId, 0);
            } else {
                int totalTuitionSubmitted = listPupilId
                        .stream()
                        .mapToInt(mapTotalTuitionSubmittedByPupilId::get).sum();
                mapResult.put(classId, totalTuitionSubmitted);
            }

        });
        return mapResult;
    }

    private Map<String, Integer> getMapTotalTuitionReceivedByGrade() {
        Map<Long, Integer> mapTotalTuitionSubmittedByPupilId = getMapTotalTuitionSubmittedByPupil();

        Set<Long> listTuitionId = mapTotalTuitionSubmittedByPupilId.keySet();
        Map<String, List<Long>> mapListPupilIdByGrade = pupilAccountService.getMapListPupilIdByGrade(listTuitionId);

        Map<String, Integer> mapResult = new HashMap<>(Constant.setGrade.size());
        Constant.setGrade.forEach(grade -> {
            List<Long> listPupilId = mapListPupilIdByGrade.get(grade);
            if (listPupilId == null) {
                mapResult.put(grade, 0);
            } else {
                mapResult.put(grade, listPupilId
                        .stream()
                        .mapToInt(mapTotalTuitionSubmittedByPupilId::get).sum());
            }
        });

        return mapResult;
    }

    private Map<Long, Integer> getMapTotalTuitionSubmittedByPupil() {
        List<PupilIdAndTuitionSubmitted> listObjectTuitionIdAndTotalTuitionSubmitted = historyPayTuitionRepository
                .getListObjectTuitionIdAndTotalTuitionSubmitted();

        Map<Long, Integer> mapTotalTuitionSubmittedByPupilId = new HashMap<>();
        listObjectTuitionIdAndTotalTuitionSubmitted.forEach(object -> {
            mapTotalTuitionSubmittedByPupilId.put(object.getPupilId(), object.getTuitionSubmitted());
        });

        return mapTotalTuitionSubmittedByPupilId;
    }

    private RevenueTuitionDto getRevenueForAllSchool() {
        int totalPupil = pupilAccountService.countTotalPupil();
        int tuitionRequire = tuitionService.getTotalTuitionRequire();

        Long tuitionReceivedLong = historyPayTuitionRepository.getTotalTuitionReceived();
        int tuitionReceived = tuitionReceivedLong != null ? ((Long) tuitionReceivedLong).intValue() : 0;

        boolean isDoneTuition = tuitionRequire == tuitionReceived;
        return new RevenueTuitionDto(totalPupil, tuitionRequire, tuitionReceived, isDoneTuition);
    }

    public ServerResponseDto detail(Long pupilId) {
        PupilAccountEntity pupilAccountEntity = pupilAccountService.findById(pupilId);
        if (pupilAccountEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        String pupilName = pupilAccountEntity.getName();
        int tuitionRequire = tuitionService.getTuitionByGrade(pupilAccountEntity.getGrade());
        Long tuitionSubmittedLong = historyPayTuitionRepository.getTuitionSubmittedByPupilId(pupilId);
        int tuitionSubmitted = tuitionSubmittedLong != null ? tuitionSubmittedLong.intValue() : 0;
        int tuitionRemain = tuitionRequire - tuitionSubmitted;

        PupilTuitionDto pupilTuitionDto = new PupilTuitionDto(pupilName, tuitionRequire, tuitionRemain);
        return new ServerResponseDto(ResponseCase.SUCCESS, pupilTuitionDto);
    }

    public ServerResponseDto updateTuition(UpdateTuitionRequestDto tuitionRequestDto) {
        Long pupilId = tuitionRequestDto.getPupilId();
        Long tuitionAdd = tuitionRequestDto.getTuitionAdd();
        if (pupilId == null || tuitionAdd == null || tuitionAdd == 0) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }

        HistoryPayTuitionEntity historyPayTuitionEntity = new HistoryPayTuitionEntity(pupilId, tuitionAdd, new Date());
        historyPayTuitionRepository.save(historyPayTuitionEntity);

        return new ServerResponseDto(ResponseCase.SUCCESS);
    }
}
