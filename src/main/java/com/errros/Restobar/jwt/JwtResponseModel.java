package com.errros.Restobar.jwt;

import com.errros.Restobar.entities.User;
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
