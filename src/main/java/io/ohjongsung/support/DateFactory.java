package io.ohjongsung.support;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ohjongsung on 2017-05-07. KST 타임존의 현재시간 리턴
 */
@Component
public class DateFactory {
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("KST");

    public Date now() {
        return new Date();
    }

    public TimeZone timeZone() {
        return DEFAULT_TIME_ZONE;
    }
}
