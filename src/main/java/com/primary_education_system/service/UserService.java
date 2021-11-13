package com.primary_education_system.service;

import com.primary_education_system.dto.ResponseCase;
import com.primary_education_system.dto.ServerResponseDto;
import com.primary_education_system.dto.account.AccountRequestDto;
import com.primary_education_system.dto.account.AccountResponseDto;
import com.primary_education_system.entity.user.RoleEntity;
import com.primary_education_system.entity.user.UserEntity;
import com.primary_education_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public AccountResponseDto getDetail(Long id) {
        UserEntity userEntity = userRepository.findByIdAndIsDeletedFalse(id);
        if (userEntity == null) {
            return null;
        }
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setPhone(userEntity.getPhone());
        accountResponseDto.setEmail(userEntity.getEmail());
        accountResponseDto.setId(userEntity.getId());
        accountResponseDto.setName(userEntity.getName());
        accountResponseDto.setBirthday(userEntity.getBirthday());
        accountResponseDto.setAddress(userEntity.getAddress());
        return accountResponseDto;
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

}
