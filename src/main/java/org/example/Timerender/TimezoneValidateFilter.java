package org.example.Timerender;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(value = {"/time"})
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String timeZone = req.getParameter("timezone");
        String regex = "^UTC[+-]([1-9]|1[0-4])(:[0-5][0-9])?$";


        if (timeZone != null) {
            String [] queryString = req.getQueryString().split("&");
            for(String query: queryString){
                String [] param = query.split("=");
                if(param[0].equals("timezone")){
                    timeZone = param[1].toUpperCase();
                }
            }
            res.getWriter().write(timeZone+"  doFilter\n\n");

            String cleaned = timeZone.trim().replaceAll("\\s+", "");
            if (!cleaned.matches(regex)) {
                res.setStatus(400);

                res.setContentType("application/json");
                res.getWriter().write("Invalid timezone\n");
                res.getWriter().write(cleaned+"\n");
            } else {
                chain.doFilter(req, res);
            }
        } else {
            chain.doFilter(req, res);
        }
    }
}
