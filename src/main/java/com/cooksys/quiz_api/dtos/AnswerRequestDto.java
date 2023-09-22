package com.cooksys.quiz_api.dtos;

import com.cooksys.quiz_api.entities.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AnswerRequestDto {

    private String text;

    private boolean correct = false;

    // this might need removing
    private Question question;

}
