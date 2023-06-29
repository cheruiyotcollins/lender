package com.ezra.lender.service;


import com.ezra.lender.dto.GeneralResponse;
import com.ezra.lender.model.User;
import com.ezra.lender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final  UserRepository userRepository;
    GeneralResponse generalResponse =new GeneralResponse();;

    public ResponseEntity<?> addUser(User user){
        try{
            userRepository.save(user);
            generalResponse.setStatus(HttpStatus.ACCEPTED);
            generalResponse.setDescription("User Created Successfully");

            return new ResponseEntity<>(generalResponse, HttpStatus.OK);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("User not created");

            return new ResponseEntity<>(generalResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findUserById(long id){

        try{

            return new ResponseEntity<>( userRepository.findById(id).get(),HttpStatus.OK);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("User Not Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> findAll(){

        try{

            return new ResponseEntity<>( userRepository.findAll(),HttpStatus.OK);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("No User Found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById(long id){

        try{
            generalResponse.setStatus(HttpStatus.ACCEPTED);
            generalResponse.setDescription("User deleted successfully");
            userRepository.deleteById(id);
            return new ResponseEntity<>( generalResponse,HttpStatus.OK);

        }catch(Exception e){
            generalResponse.setStatus(HttpStatus.BAD_REQUEST);
            generalResponse.setDescription("User with that id not found");
            return new ResponseEntity<>( generalResponse,HttpStatus.BAD_REQUEST);
        }
    }
}
