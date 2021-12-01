package com.primary_education_system.service;

import com.primary_education_system.config.security.CustomUserDetails;
import com.primary_education_system.dto.EmailTemplate;
import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.account.AccountRequestDto;
import com.primary_education_system.dto.account.ChangePasswordRequestDto;
import com.primary_education_system.entity.token.TokenEntity;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.repository.UserRepository;
import com.primary_education_system.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${host}")
    public String host;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubjectTeacherService subjectTeacherService;

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
        return userRepository.findByIdAndIsDeletedFalse(id);
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

    public ServerResponseDto setPassword(String token, String password) {
        TokenEntity tokenEntity = tokenService.validateToken(token, 4);
        if (tokenEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        UserEntity userEntity = userRepository.findOne(tokenEntity.getUserId());
        if (userEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRawPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
        tokenService.save(tokenEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }


    public List<UserEntity> getListTeacher() {
        return userRepository.getListTeacher();
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

    public Map<Long, String> getMapNameById(List<Long> listTeacherId) {
        if (listTeacherId.isEmpty()) {
            return new HashMap<>();
        }
        List<UserEntity> listUser = userRepository.getListUserByListId(listTeacherId);
        return listUser
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, UserEntity::getName));
    }

    public ServerResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto,
                                            CustomUserDetails customUserDetails) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(customUserDetails.getUserId());
        if (userEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        String currentPassword = userEntity.getPassword();
        String currentPasswordRequest = changePasswordRequestDto.getCurrentPassword();

        if (!passwordEncoder.matches(currentPasswordRequest, currentPassword)) {
            return new ServerResponseDto(ResponseCase.ERROR_PASSWORD);
        }
        userEntity.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepository.save(userEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public UserEntity findByIdAndIsDeletedFalse(Long teacherId) {
        return userRepository.findByIdAndIsDeletedFalse(teacherId);
    }

    public ServerResponseDto forgotPassword(String email) {
        UserEntity userEntity = userRepository.findFirstByEmail(email);
        if (userEntity == null) {
            return new ServerResponseDto(ResponseCase.ERROR);
        }
        String tokenString = tokenService.generateToken();
        String confirmationUrl = host + "confirmForgotPassword?token=" + tokenString;
        String subject = "Quên mật khẩu";
        EmailTemplate emailTemplate = new EmailTemplate(email, subject, confirmationUrl);
        emailService.sendMail(emailTemplate);

        TokenEntity tokenEntity = new TokenEntity(userEntity.getId(), tokenString, 4);
        tokenService.save(tokenEntity);
        return new ServerResponseDto(ResponseCase.SUCCESS);
    }

    public boolean confirmForgotPassword(String token) {
        TokenEntity tokenEntity = tokenService.validateToken(token, 4);
        return tokenEntity != null;
    }

    public String getNameUserById(Long homeroomTeacherId) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(homeroomTeacherId);
        return userEntity.getName();
    }

    public List<UserEntity> getListHomeroomTeacher() {
        return userRepository.getListHomeroomTeacher();
    }

    public void setTeacherIsHomeRoomTeacher(Long homeroomTeacherIdOld, Long homeroomTeacherIdNew) {
        if (homeroomTeacherIdOld != null) {
            UserEntity teacherOld = userRepository.findByIdAndIsDeletedFalse(homeroomTeacherIdOld);
            teacherOld.setHomeroomTeacher(false);
            userRepository.save(teacherOld);
        }

        UserEntity teacherNew = userRepository.findByIdAndIsDeletedFalse(homeroomTeacherIdNew);
        if (teacherNew == null) {
            return;
        }
        teacherNew.setHomeroomTeacher(true);
        userRepository.save(teacherNew);
    }

    public List<UserEntity> findByIdInAndIsDeletedFalse(List<Long> listTeacherId) {
        return userRepository.findByIdInAndIsDeletedFalse(listTeacherId);
    }

    public List<UserEntity> getListTeacherForSubject(Long subjectId) {
        return subjectTeacherService.getListTeacherBySubjectId(subjectId);
    }

    public void updateStatusHomeroom(Long userId) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(userId);
        if (userEntity == null) {
            return;
        }
        userEntity.setHomeroomTeacher(false);
    }
}
