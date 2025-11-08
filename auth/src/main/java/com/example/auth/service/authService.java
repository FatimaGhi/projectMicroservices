package com.example.auth.service;


import com.example.auth.Entites.LoginRequest;
import com.example.auth.Entites.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class authService {


    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private UserDetailsService userDetailsService;

    public authService(AuthenticationManager authenticationManager,JwtEncoder jwtEncoder,JwtDecoder jwtDecoder,UserDetailsService userDetailsService){
        this.authenticationManager=authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService= userDetailsService;
    }

    public Map<String,String>  login(LoginRequest loginRequest){

        Map<String,String>  ID_token = new HashMap<>();
        Instant  instant = Instant.now();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                ));
        // création des ID Token
        //1 -Acess token

        JwtClaimsSet jwtClaimsSet_acessToken = JwtClaimsSet.builder()
                .subject(authenticate.getName())
                .issuer("auth_fatima")
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("scope",authenticate.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .build();

        String Access_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_acessToken)).getTokenValue();

        // refresh token

        JwtClaimsSet jwtClaimsSet_refreshToken = JwtClaimsSet.builder()
                .subject(authenticate.getName())
                .issuer("auth_fatima")
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.MINUTES))
                .build();

        String Refresh_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_refreshToken)).getTokenValue();

        ID_token.put("Access_Token",Access_token);
        ID_token.put("Refresh_Token",Refresh_token);
        return ID_token;
    }




    public Map<String,String>  refrech(String refreshToken){
        Map<String,String> ID_Token= new HashMap<>();
        String username = jwtDecoder.decode(refreshToken).getSubject();

        if (refreshToken == null){
            ID_Token.put("Error","Refresh token est null"+ HttpStatus.UNAUTHORIZED);
            return ID_Token;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Instant instant = Instant.now();
        // create new  refresh Token
        JwtClaimsSet jwtClaimsSet_refreshToken = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .issuer("auth_fatima_Refresh")
                .issuedAt(instant)
                .expiresAt(instant.plus(15, ChronoUnit.MINUTES))
                .build();

        String Refresh_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_refreshToken)).getTokenValue();

        JwtClaimsSet jwtClaimsSet_acessToken = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .issuer("auth_fatima_Refresh")
                .issuedAt(instant)
                .expiresAt(instant.plus(2, ChronoUnit.MINUTES))
                .claim("scope",userDetails.getAuthorities().stream()
                        .map(auth ->   auth.getAuthority())
                        .toList())
                .build();

        String Access_token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_acessToken)).getTokenValue();

        ID_Token.put("Access_Token",Access_token);
        ID_Token.put("Refresh_Token",Refresh_token);
        return ID_Token;

    }


    }

