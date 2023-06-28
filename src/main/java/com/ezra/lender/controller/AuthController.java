package com.ezra.lender.controller;


import com.ezra.lender.dto.AddRoleRequest;
import com.ezra.lender.dto.JWTAuthResponse;
import com.ezra.lender.dto.LoginDto;
import com.ezra.lender.dto.SignUpRequest;
import com.ezra.lender.model.User;
import com.ezra.lender.repository.RoleRepository;
import com.ezra.lender.repository.UserRepository;
import com.ezra.lender.service.AuthService;
import com.ezra.lender.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/auth")
public class AuthController {
        private AuthService authService;

        @Autowired
        UserService userService;
         @Autowired
         UserRepository userRepository;
         @Autowired
         RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

//     Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest){
        return authService.register(signUpRequest);
    }



    @PutMapping("update")
    public ResponseEntity<?> updateUser(User user){
        return userService.addUser(user);

    }
    @GetMapping("findById/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return userService.findUserById(id);

    }
    @GetMapping("list/all")
    public ResponseEntity<?> findAll(){
        return userService.findAll();

    }
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return userService.deleteById(id);

    }
    @PostMapping("new/role")
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest addRoleRequest){
        return authService.addRole(addRoleRequest);
    }
}
