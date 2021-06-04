package com.group_3.kanbanboard.util;

import com.group_3.kanbanboard.exception.FormInputException;
import java.util.Date;

public class DateUtil {

  public static boolean checkIncorrectDates(Date startDate, Date endDate){
    if(startDate.after(endDate)){
      throw new FormInputException("Start date of release should be earlier than end date");
    }
    return true;
  }

}
