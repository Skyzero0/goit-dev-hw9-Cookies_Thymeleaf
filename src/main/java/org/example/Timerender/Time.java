package org.example.Timerender;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public String getTime (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String timeZoneFromCookie = getTimeZoneFromCookie(request,  response);
        String timeZone;

        if (request.getParameterMap().containsKey("timezone")) {
            timeZone = request.getParameter("timezone").replace(" ","+").toUpperCase();

            Cookie lastTimezone = new Cookie("lastTimezone", timeZone);
            lastTimezone.setMaxAge(7);
            response.addCookie(lastTimezone);

            return ZonedDateTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern(
                    "dd.MM.yyyy HH:mm:ss"
            ))+ " " + timeZone;
        }

        else if (timeZoneFromCookie!=null) {
            timeZone = timeZoneFromCookie;
            return ZonedDateTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern(
                    "dd.MM.yyyy HH:mm:ss"
            ))+ " " + timeZone;
        }

        return ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern(
                "dd.MM.yyyy HH:mm:ss z"
        ));
    }

    private String getTimeZoneFromCookie (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String timeZoneCookie = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("lastTimezone")) {
                timeZoneCookie = cookie.getValue();
                response.getWriter().write("\n Time zone in Cookie " + timeZoneCookie + "\n");
            }
        }

        return timeZoneCookie;
    }
}
