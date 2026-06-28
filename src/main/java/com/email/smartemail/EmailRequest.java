package com.email.smartemail;

import lombok.Data;

@Data
public class EmailRequest {
    private String emailContent;
    private String tone;
}
