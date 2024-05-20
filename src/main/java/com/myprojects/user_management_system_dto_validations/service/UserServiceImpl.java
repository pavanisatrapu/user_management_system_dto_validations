package com.myprojects.user_management_system_dto_validations.service;

import com.myprojects.user_management_system_dto_validations.dto.UserDto;
import com.myprojects.user_management_system_dto_validations.entity.User;
import com.myprojects.user_management_system_dto_validations.exceptions.EmailAlreadyExistsException;
import com.myprojects.user_management_system_dto_validations.exceptions.ResourceNotFoundException;
import com.myprojects.user_management_system_dto_validations.mapper.UserMapperMapStruct;
import com.myprojects.user_management_system_dto_validations.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        //dto to jpa entity
        Optional<User> optionalUser= Optional.ofNullable(userRepository.findByUserEmail(userDto.getUserEmail()));
        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for User");
        }
        User user= UserMapperMapStruct.MAPPER.toUser(userDto);
        User savedUser =userRepository.save(user);

        //jpa entity to dto
        return UserMapperMapStruct.MAPPER.toUserDto(user);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        return Optional.ofNullable(UserMapperMapStruct.MAPPER.toUserDto(user));
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> userDtoList= new ArrayList<UserDto>();
        List<User> userList=userRepository.findAll();
        int userListSize=userList.size();
        for (int i=0;i<userListSize;i++){
            userDtoList.add(UserMapperMapStruct.MAPPER.toUserDto(userList.get(i)));
        }
        return userDtoList;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDto.getUserId())
        );
        User user=new User(userDto.getUserId(), userDto.getUserName(), userDto.getUserEmail(), userDto.getUserAge());

        existingUser.setUserName(user.getUserName());
        existingUser.setUserEmail(user.getUserEmail());
        existingUser.setUserAge(user.getUserAge());
        User updatedUser =userRepository.save(user);
        return UserMapperMapStruct.MAPPER.toUserDto(user);
    }

    @Override
    public UserDto deleteUser(Long id) {
        User wantToDelete = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        userRepository.deleteById(id);
        return UserMapperMapStruct.MAPPER.toUserDto(wantToDelete);
    }


}
