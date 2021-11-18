package com.primary_education_system.service;

import com.google.common.collect.Lists;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.account.AccountRequestDto;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.enum_type.Roles;
import com.primary_education_system.repository.UserRepository;
import com.primary_education_system.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    public Page<UserEntity> getListAccount(Pageable pageable, String keyword) {
        return userRepository.getListAccount(keyword, pageable);
    }

    public ServerResponseDto save(AccountRequestDto saveDto) {
        boolean isEmailExist = userRepository.countByEmailAndId(saveDto.getEmail(), saveDto.getId()) != 0;
        if (isEmailExist) {
            return new ServerResponseDto(ResponseCase.EMAIL_EXISTED);
        }
        UserEntity userEntity;
        if (saveDto.getId() == null) {
            userEntity = new UserEntity();
            userEntity.setCreatedTime(new Date());
            userEntity.setEmail(saveDto.getEmail());
            userEntity.setPassword(passwordEncoder.encode(saveDto.getPassword()));
            userEntity.setRawPassword(passwordEncoder.encode(saveDto.getPassword()));
            userEntity.setChangePassword(false);
            userEntity.setStatusUser(2);
        } else {
            userEntity = userRepository.findByIdAndIsDeletedFalse(saveDto.getId());
            if (userEntity == null) {
                return new ServerResponseDto(ResponseCase.ERROR);
            }
            userEntity.setUpdatedTime(new Date());
            if (!saveDto.getPassword().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(saveDto.getPassword()));
                userEntity.setRawPassword(passwordEncoder.encode(saveDto.getPassword()));
            }
        }
        List<String> listRoleName = new ArrayList<>(Arrays.asList(saveDto.getListRoleName().split(",")));
        List<RoleEntity> roleEntity = roleService.findByNameIn(listRoleName);
        Set<RoleEntity> roleEntities = new HashSet<>(roleEntity);
        userEntity.setName(saveDto.getName());
        userEntity.setUsername(saveDto.getEmail());
        userEntity.setPhone(saveDto.getPhone());
        userEntity.setRoles(roleEntities);
        userEntity.setDeleted(false);
        userRepository.save(userEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public UserEntity getDetail(Long id) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(id);
        if (userEntity == null) {
            return null;
        }
        return userEntity;
    }

    public ServerResponseDto delete(Long id) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(id);
        if (userEntity == null || userEntity.getId() == 1) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        userEntity.setDeleted(true);
        userEntity.setUpdatedTime(new Date());
        userRepository.save(userEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public ServerResponseDto updateProfile(Long id, AccountRequestDto saveDto) throws ParseException {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(id);
        if (userEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        boolean isEmailExist = userRepository.countByEmailAndId(saveDto.getEmail(), id) != 0;
        if (isEmailExist) {
            return new ServerResponseDto(ResponseCase.EMAIL_EXISTED);
        }
        userEntity.setName(saveDto.getName());
        userEntity.setEmail(saveDto.getEmail());
        userEntity.setPhone(saveDto.getPhone());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        userEntity.setBirthday(sdf.parse(saveDto.getBirthday()));
        userEntity.setAddress(saveDto.getAddress());
        userRepository.save(userEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public Map<Long, List<UserEntity>> getMapListTeacherBySubjectId(List<Long> listSubjectId) {
        List<UserEntity> listTeacher = userRepository.getListTeacherByListSubjectId(listSubjectId);

        return listTeacher.stream().collect(Collectors.groupingBy(UserEntity::getTeachSubjectId));
    }

    public List<UserEntity> getListTeacherCanTeach() {
        return userRepository.getListTeacherCanTeach();
    }

    @Transactional
    public void setTeachSubjectIdForTeacher(Long subjectId, String listTeacherIdString) {
        String[] arrayId = listTeacherIdString.split(",");
        List<Long> listTeacherIdAfter = Arrays.asList(arrayId).stream()
                .map(id -> Long.parseLong(id))
                .collect(Collectors.toList());

        List<UserEntity> listTeacherEntityBefore = userRepository.getListTeacherBySubjectId(subjectId);
        List<Long> listTeacherIdBefore = listTeacherEntityBefore.stream().map(UserEntity::getId).collect(Collectors.toList());

        /* remove */
        removeTeachSubjectId(listTeacherIdBefore, listTeacherIdAfter);
        /*--------*/

        /* add */
        addTeachSubjectId(listTeacherIdBefore, listTeacherIdAfter, subjectId);
        /*--------*/

        List<UserEntity> listTeacher = userRepository.getListUserByListIdAndRole(listTeacherIdAfter, Constant.TEACHER);

        listTeacher.forEach(teacher -> {
            teacher.setTeachSubjectId(subjectId);
        });

        userRepository.save(listTeacher);
    }

    private void removeTeachSubjectId(List<Long> listTeacherIdBefore, List<Long> listTeacherIdAfter) {
        if (listTeacherIdBefore.isEmpty()) {
            return;
        }
        List<Long> listIdRemoved = Lists.newArrayListWithExpectedSize(listTeacherIdBefore.size());
        listTeacherIdBefore.forEach(idBefore -> {
            if (!listTeacherIdAfter.contains(idBefore)) {
                listIdRemoved.add(idBefore);
            }
        });
        if (listIdRemoved.isEmpty()) {
            return;
        }

        List<UserEntity> listTeacherRemovedSubjectId = userRepository.getListUserByListIdAndRole(listIdRemoved, Constant.TEACHER);
        listTeacherRemovedSubjectId.forEach(teacher -> teacher.setTeachSubjectId(null));

        userRepository.save(listTeacherRemovedSubjectId);
    }

    private void addTeachSubjectId(List<Long> listTeacherIdBefore, List<Long> listTeacherIdAfter, Long subjectId) {
        List<Long> listIdAdded = new ArrayList<>();
        listTeacherIdAfter.forEach(idAfter -> {
            if (!listTeacherIdBefore.contains(idAfter)) {
                listIdAdded.add(idAfter);
            }
        });

        if (listIdAdded.isEmpty()) {
            return;
        }

        List<UserEntity> listTeacherRemovedSubjectId = userRepository.getListUserByListIdAndRole(listIdAdded, Constant.TEACHER);
        listTeacherRemovedSubjectId.forEach(teacher -> teacher.setTeachSubjectId(subjectId));

        userRepository.save(listTeacherRemovedSubjectId);
    }

    public List<UserEntity> getListTeacherBySubjectId(Long subjectId) {
        return userRepository.getListTeacherBySubjectId(subjectId);
    }

    public void removeTeachSubjectId(Long subjectId) {
        List<UserEntity> listTeacher = userRepository.getListTeacherBySubjectId(subjectId);
        listTeacher.forEach(teacher -> teacher.setTeachSubjectId(null));

        userRepository.save(listTeacher);
    }

    public List<UserEntity> getListTeacherForSubject(Long subjectId) {
        return userRepository.getListTeacherBySubjectId(subjectId);
    }

    public Map<Long, String> getMapNameTeacherById(List<Long> listTeacherId) {
        if (listTeacherId.isEmpty()) {
            return new HashMap<>();
        }
        List<UserEntity> listTeacher = userRepository.getListUserByListIdAndRole(listTeacherId, Constant.TEACHER);
        return listTeacher
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, UserEntity::getName));
    }
}
