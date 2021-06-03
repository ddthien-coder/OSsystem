package com.ossystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ossystem.entity.Role;
import com.ossystem.entity.User;
import com.ossystem.mapper.RoleMapper;
import com.ossystem.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RoleMapper rolesMapper;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper rolesMapper, BCryptPasswordEncoder encoder) {
        this.userMapper = userMapper;
        this.rolesMapper = rolesMapper;
        this.encoder = encoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find the user whose username is: "+ username + "");
        }
        List<Role> roles = rolesMapper.selectRolesByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public int register(User user) {
        if (userMapper.selectUserByEmail(user.getEmail()) != null) return -1;
        if (userMapper.selectUserByUsername(user.getUsername()) != null) return -2;
        user.setPassword(encoder.encode(user.getPassword()));
        int result = userMapper.insertUser(user);
        if (result > 0) rolesMapper.insertRoleToUser(user.getId(), 2);
        return result;
    }

    @Override
    public User getUserById(int id) {
        User user = userMapper.selectUserById(id);
        if (user == null) return new User();
        user.setPassword(null);
        return user;
    }
}
