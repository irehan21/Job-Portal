package com.jpbportal.services;

import com.jpbportal.dto.LogInDTO;
import com.jpbportal.dto.ResponseDTO;
import com.jpbportal.dto.UserDTO;
import com.jpbportal.exception.JobPortalException;
import jakarta.validation.Valid;


public interface UserService {
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException;

   public UserDTO loginUser( LogInDTO loginDTO) throws JobPortalException;

   public Boolean sendOtp (String email) throws  Exception;

   public Boolean verifyOtp(String email, String otp) throws JobPortalException;

  public   ResponseDTO changePassword( LogInDTO loginDTO) throws JobPortalException;
}
