package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getTimeStamp() {

        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new Date());
    }
}