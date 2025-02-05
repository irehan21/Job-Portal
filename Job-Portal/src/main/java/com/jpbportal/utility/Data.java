package com.jpbportal.utility;

public class Data {

    public  static String getMessageBody (String otp, String name) {
        return  "Subject: OTP Verification Code\n\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>OTP Verification</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }\n" +
                "        .container { max-width: 600px; margin: 50px auto; background: #ffffff; padding: 20px; text-align: center; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }\n" +
                "        .otp { font-size: 24px; font-weight: bold; color: #333; background: #f8f8f8; display: inline-block; padding: 10px 20px; border-radius: 5px; margin: 20px 0; }\n" +
                "        .footer { margin-top: 20px; font-size: 14px; color: #777; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h2>OTP Verification</h2>\n" +
                "        <p>Dear "+name+",</p>\n" +
                "        <p>Your One-Time Password (OTP) for verification is:</p>\n" +
                "        <div class=\"otp\">"+otp+"</div>\n" +
                "        <p>This OTP is valid for {{10 Min}} minutes. Do not share it with anyone.</p>\n" +
                "        <p>If you did not request this code, please ignore this email or contact support.</p>\n" +
                "        <p class=\"footer\">Best regards,<br> Job Hook </p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

    }
}
