package se.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import se.domain.User;
import se.service.UserService;
import se.service.UserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static se.config.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(3);
        String header = request.getHeader(HEADER_STRING);
        if(header==null || !header.startsWith(TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuth(request);
        System.out.println("3.past " + authenticationToken);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationToken);
        System.out.println("UserDetails " + authenticationToken.getCredentials());
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest request){
        System.out.println(4);
        String token = request.getHeader(HEADER_STRING);
        if(token != null){
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX,""))
                    .getSubject();
            System.out.println(user);
            if (user != null){
                UserService users = new UserServiceImpl();
                User user1 = users.getUserByUsername(user);

                UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                        .username(user1.getLogin())
                        .password(user1.getPassword())
                        .roles(user1.getRole())
                        .build();

                return new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities());
            }
            return  null;
        }
        return null;
    }
    //getAuthentication org.springframework.security.authentication.UsernamePasswordAuthenticationToken@ffd81c0f:
    // Principal: USER; Credentials: [PROTECTED]; Authenticated: true; Details: null; Not granted any authorities


}
