package com.bookingService.filter;

import com.bookingService.service.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get Token
        String requestToken = request.getHeader("Authorization");
        System.out.println(requestToken);

        //now get
        String userName=null;
        String token=null;

        if (requestToken !=null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7);

            try {
                userName = jwtTokenService.getUserNameFromToken(token);
            }catch (IllegalArgumentException e){
                logger.error("An error occurred while fetching Username from Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired", e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        }else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }
        //Once we get Token,now validate
        if (userName !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtTokenService.validateToken(token,userDetails)){
                //nowAuthenticate

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken
                        (userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                System.out.println("Invalid JWT Token");
            }
        }else {
            System.out.println("Username Authentication null And Context is not null");
        }

        filterChain.doFilter(request,response);
    }
}
