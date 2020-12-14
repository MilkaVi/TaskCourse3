package se.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.domain.User;
import se.repository.UserRepositoryImpl;

import static java.util.Collections.emptyList;

@Component
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepositoryImpl userService = new UserRepositoryImpl();




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User myUser= userService.getUserByUsername(s);
        System.out.println("customuserDeatils " + myUser);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+s);
        }
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        System.out.println("userDet " + user);
        return user;
        //return new org.springframework.security.core.userdetails.User(myUser.getLogin(), myUser.getPassword(), emptyList());
    }

//    @Override
//    public  CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User userEntity = userService.getUserByUsername(username);
//        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
//    }
}