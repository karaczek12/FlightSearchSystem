package org.fss.service;

import org.fss.dto.UserDto;
import org.fss.entity.User;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

}
