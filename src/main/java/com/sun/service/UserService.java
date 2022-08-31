package com.sun.service;

import com.sun.common.CurrentUser;
import com.sun.entity.User;
import com.sun.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = getUsers(username);
        if (users.isEmpty())
            throw new UsernameNotFoundException("User does not exits!!!");

        User user = users.get(0);
        CurrentUser.currentUser = user;
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRole())));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public List<User> getUsers(String email) {
        return userRepository.getAllByEmail(email);
    }

    @Override
    public boolean addUser(User user) {
        String password = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(password));

        try {
            if (userRepository.save(user) == null)
                throw new HibernateException("User: " + user.getName() + "cannot save");
            return true;
        } catch (HibernateException exception) {
            exception.getMessage();
            return false;
        }
    }
}
