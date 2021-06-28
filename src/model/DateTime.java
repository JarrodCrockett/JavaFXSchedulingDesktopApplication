package model;
/**
 *
 * @author Jarrod Crockett
 */
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**DateTime class. This class is used to get the UTC zone date time, eastern zone date time and test for business hours in eastern zone date time.*/
public class DateTime {

    /**This Method returns the current time in the eastern zone date time.
     * @param currentTime The current time in the users computer.
     * @return Returns the eastern Zone Date Time.*/
    public static ZonedDateTime cstZDT(ZonedDateTime currentTime){

        return currentTime.withZoneSameInstant(DateTime.cstZoneId());
    }

    /**This Method returns the zone ID for the eastern zone date time.
     * @return Returns the eastern Zone ID.*/
    public static ZoneId cstZoneId(){
        return ZoneId.of("America/Chicago");
    }

    /**This method checks if the time attempted to set the appointment for is within business hours of eastern zone date time.
     * @param currentStartTime the start time of the appointment.
     * @param currentEndTime the end time of the appointment.*/
    public static boolean cstBusinessHours(ZonedDateTime currentStartTime, ZonedDateTime currentEndTime){

        LocalTime estMorningZT = LocalTime.of(7,59);
        LocalTime estEveningZT = LocalTime.of(22,0);

        ZonedDateTime cstMorningZDT = ZonedDateTime.of(currentStartTime.toLocalDate(),estMorningZT,DateTime.cstZoneId());
        ZonedDateTime cstEveningZDT = ZonedDateTime.of(currentStartTime.toLocalDate(),estEveningZT,DateTime.cstZoneId());


        if (DateTime.cstZDT(currentStartTime).getDayOfWeek().getValue() == 6 || DateTime.cstZDT(currentStartTime).getDayOfWeek().getValue() == 7){
            return false;
        }

        if (cstMorningZDT.isBefore(DateTime.cstZDT(currentStartTime)) && cstEveningZDT.isAfter(DateTime.cstZDT(currentStartTime))
        && cstEveningZDT.isAfter(DateTime.cstZDT(currentEndTime))) {
            return true;
        }


        return false;
    }

    /**This method returns the UTC zone date time for the current time.
     * @param timeToConvert The current time to be converted to UTC
     * @return Returns the UTC zoned date time*/
    public static ZonedDateTime utcZDT(ZonedDateTime timeToConvert){
        return timeToConvert.withZoneSameInstant(ZoneId.of("UTC"));
    }


}
