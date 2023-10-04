package com.example.bookshop.seccurity;

import com.example.bookshop.dao.CustomerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@RequiredArgsConstructor
@Configuration
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomerDao customerDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerDao.findByName(username)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("Not found"));
    }
}
