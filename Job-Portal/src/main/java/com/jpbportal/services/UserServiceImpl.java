package com.jpbportal.services;

import com.jpbportal.dto.LogInDTO;
import com.jpbportal.dto.ResponseDTO;
import com.jpbportal.dto.UserDTO;
import com.jpbportal.entity.OTP;
import com.jpbportal.entity.User;
import com.jpbportal.exception.JobPortalException;
import com.jpbportal.repository.OtpRepository;
import com.jpbportal.repository.UserRepository;
import com.jpbportal.utility.Data;
import com.jpbportal.utility.Utilities;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProfileService profileService;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobPortalException {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) throw new JobPortalException("USER_FOUND");
        userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO loginUser(LogInDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) throw new JobPortalException("INVALID_CREDENTIALS");
        return user.toDTO();
    }

    @Override
    public Boolean sendOtp(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("OTP");
        String genOtp = Utilities.generateOTP();
        OTP otp = new OTP(email,genOtp, LocalDateTime.now());
        otpRepository.save(otp);
        mimeMessageHelper.setText(Data.getMessageBody(genOtp,user.getName()), true);
        mailSender.send(mimeMessage);
        return true;
    }

    @Override
    public Boolean verifyOtp(String email, String otp) throws JobPortalException {
        OTP otpEntity = otpRepository.findById(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        if (!otpEntity.getOtpCode().equals(otp)) throw new JobPortalException("INVALID_OTP");
        return true;
    }

    @Override
    public ResponseDTO changePassword(LogInDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        userRepository.save(user);
        return new ResponseDTO("Password changed successfully");
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTPs() {
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(5);
        List<OTP> expiryOTPs = otpRepository.findByCreationTimeBefore(expiry);
        if (!expiryOTPs.isEmpty()) {
            otpRepository.deleteAll(expiryOTPs);
            System.out.println("Removed expired OTPs " + expiryOTPs.size());
        }
    }
}
