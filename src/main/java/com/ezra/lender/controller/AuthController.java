package com.ezra.lender.controller;


import com.ezra.lender.dto.AddRoleRequest;
import com.ezra.lender.dto.JWTAuthResponse;
import com.ezra.lender.dto.LoginDto;
import com.ezra.lender.dto.SignUpRequest;
import com.ezra.lender.model.Role;
import com.ezra.lender.model.User;
import com.ezra.lender.repository.RoleRepository;
import com.ezra.lender.repository.UserRepository;
import com.ezra.lender.service.AuthService;
import com.ezra.lender.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/auth")
public class AuthController {
    @Autowired
         AuthService authService;

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


     @Operation(summary = "User sign in/ login")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User Logged In Successfully",
                content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
        @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
        @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping(value = {"/login", "/signin"})
     @PreAuthorize("permitAll()")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @Operation(summary = "New User Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping(value = {"/register", "/signup"})
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest){
        return authService.register(signUpRequest);
    }


    @Operation(summary = "Update User Info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Information Updated Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})

    @PutMapping("update")
    public ResponseEntity<?> updateUser(User user){
        return userService.addUser(user);

    }
    @Operation(summary = "Find User By  his/her Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "User found",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("findById/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return userService.findUserById(id);

    }
    @Operation(summary = "List All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "returned list of users",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "no user found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @GetMapping("list/all")
    public ResponseEntity<?> findAll(){
        return userService.findAll();

    }
    @Operation(summary = "Delete User By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Deleted Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return userService.deleteById(id);

    }
    @Operation(summary = "Create New Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role Created Successfully",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Role.class))}),
            @ApiResponse(responseCode = "401",description = "Unauthorized user",content = @Content),
            @ApiResponse(responseCode = "404",description = "Role not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content)})
    @PostMapping("new/role")
    public ResponseEntity<?> addRole(@RequestBody AddRoleRequest addRoleRequest){
        return authService.addRole(addRoleRequest);
    }
}
