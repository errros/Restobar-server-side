package com.errros.Restobar.authentication.jwt;

import com.errros.Restobar.authentication.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseModel {
    private String jwtToken;
    private User user;
}
