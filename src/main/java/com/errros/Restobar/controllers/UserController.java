package com.errros.Restobar.controllers;


import com.errros.Restobar.entities.Cashier;
import com.errros.Restobar.entities.Owner;
import com.errros.Restobar.models.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserController {




/*
    @PatchMapping("owner/{id}")
    public ResponseEntity<String> updateOwner(@PathVariable("id")Long id , @RequestBody @Valid UserRequest owner){



    }

    @PatchMapping("cashier/{id}")
    public ResponseEntity<String> registerCashier(@RequestBody @Valid Cashier cashier){

    }
*/



}

