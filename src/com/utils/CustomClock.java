package com.utils;

import java.time.*;


public class CustomClock extends Clock {
    private ZonedDateTime dateTime;
    private int hoursOffset = 0;
    private int minutesOffset = 0;
    private int tickSpeed = 1;
    private ZonedDateTime customDateTime;

    public CustomClock() {
       dateTime = ZonedDateTime.now();
    }

    @Override
    public ZoneId getZone() {
        return dateTime.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }

    @Override
    public Instant instant() {
        return dateTime.toInstant();
    }

    public void plusHour() {
        this.hoursOffset++;
    }

    public void minusHour() {
        this.hoursOffset--;
    }

    public void plusMinute() {
        this.minutesOffset++;
    }

    public void minusMinute() {
        this.minutesOffset--;
    }

    public CustomClock setTime(String newClockTime) {
        LocalTime time = LocalTime.parse(newClockTime);
        customDateTime = dateTime.withHour(time.getHour()).withMinute(time.getMinute()).withSecond(time.getSecond());
        hoursOffset = 0;
        minutesOffset = 0;
        return this;
    }

    public LocalTime getTime() {
        ZonedDateTime now = ZonedDateTime.now();
        long nowSeconds = now.toInstant().getEpochSecond();
        long beginSeconds = this.dateTime.toInstant().getEpochSecond();

        long delta;
        if (customDateTime != null) {
            long customSeconds = this.customDateTime.toInstant().getEpochSecond();
            delta = nowSeconds - customSeconds;
        } else {
            delta = nowSeconds - beginSeconds;
        }

        ZonedDateTime newDateTime;
        newDateTime = this.dateTime.plusHours(hoursOffset).plusMinutes(minutesOffset).plusSeconds(Math.round(Math.abs(delta) * tickSpeed));

        return LocalTime.of(newDateTime.getHour(), newDateTime.getMinute(), newDateTime.getSecond());
    }
}
