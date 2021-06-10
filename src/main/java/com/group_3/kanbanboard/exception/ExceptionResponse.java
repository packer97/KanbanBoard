package com.group_3.kanbanboard.exception;

import java.util.Date;

/**
 * Class for body of response if request call {@link Exception} or inherited class
 */
public class ExceptionResponse {

    private Date occurrenceTime;
    private String message;

    public ExceptionResponse(Date occurrenceTime, String message) {
        this.occurrenceTime = occurrenceTime;
        this.message = message;

    }

    public Date getOccurrenceTime() {
        return occurrenceTime;
    }


    public void setOccurrenceTime(Date occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
