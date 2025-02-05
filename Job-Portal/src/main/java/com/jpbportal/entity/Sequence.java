package com.jpbportal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
@Data
//@Getter
//@Setter
public class Sequence {
    @Id
    private String id;
    private Long seq;
}
