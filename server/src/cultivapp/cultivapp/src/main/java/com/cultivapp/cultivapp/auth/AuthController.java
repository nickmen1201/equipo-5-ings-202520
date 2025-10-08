package com.cultivapp.cultivapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // para Vite en dev
public class AuthController {

    private final AuthService auth;

    @PostMapping("/login")
    public ResponseEntity<AuthService.LoginResponse> login(@RequestBody AuthService.LoginRequest body){
        var res = auth.login(body.email(), body.password());
        return ResponseEntity.ok(res);
    }

    @ExceptionHandler(AuthService.AuthException.class)
    public ResponseEntity<?> handleBadCreds(AuthService.AuthException ex){
        return ResponseEntity.status(401).body(new ErrorMsg(ex.getMessage()));
    }

    @ExceptionHandler(AuthService.DisabledException.class)
    public ResponseEntity<?> handleDisabled(AuthService.DisabledException ex){
        return ResponseEntity.status(403).body(new ErrorMsg(ex.getMessage()));
    }

    record ErrorMsg(String message){}
}
