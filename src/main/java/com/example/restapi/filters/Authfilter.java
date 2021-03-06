package com.example.restapi.filters;

import com.example.restapi.Secrets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Authfilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

      
        String authHeader = httprequest.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer");
            if(authHeaderArr.length>1 && authHeaderArr[1]!=null){
                String token = authHeaderArr[1];
                try {
                    Claims claim = Jwts.parser().setSigningKey(Secrets.API_KEY).parseClaimsJws(token).getBody();
                    httprequest.setAttribute("userId",Integer.parseInt(claim.get("user_id").toString()));


                }
                catch (Exception e){
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"invalid/expired token");
                    return;
                }
            }
            else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Authorization token must be Bearer [token] ");
                return;
            }

        }else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Authorization token must be provided");
            return;
        }




/*
        httpResponse.setHeader("Access-Control-Allow-Origin", httprequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization");
*/
        chain.doFilter(request,response);


    }
}
