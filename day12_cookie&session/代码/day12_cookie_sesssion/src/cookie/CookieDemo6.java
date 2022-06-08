package cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/cookieDemo6")
public class CookieDemo6 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应的消息体的数据格式以及编码
        resp.setContentType("text/html;charset=utf-8");
        Cookie[] cookies = req.getCookies();
        boolean flag = false;// 没有cookie为lasttime

        if(cookies != null && cookies.length >0 ){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if("lastTime".equals(name)){
                    // 有lastTime这个cookie
                    flag = true;
                    String value = cookie.getValue();
                    // 解码 年月日在cookie中是乱的，2022%E5%B9%B406%E6%9C%8806%E6%97%A5+12%3A01%3A21
                    value = URLDecoder.decode(value,"utf-8");
                    System.out.println(value);

                    Date date = new Date();
                    // Mon Jun 06 11:07:54 CST 2022
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    String dateTemp = sdf.format(date);
                    dateTemp = URLEncoder.encode(dateTemp,"utf-8");
                    cookie.setValue(dateTemp);

                    // 先改时间，再打印到dom
                    resp.getWriter().write("<h1>欢迎回来，您上次访问的时间是：" + value + "<h1>");
                    break;
                }
            }
        }
        if(cookies == null || cookies.length == 0 || flag == false ){
            Date date = new Date();
            System.out.println(date);
            // Mon Jun 06 11:07:54 CST 2022
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String dateTemp = sdf.format(date);
            System.out.println(dateTemp);
            // 先编码成utf-8
            dateTemp = URLEncoder.encode(dateTemp,"utf-8");

            Cookie cookie = new Cookie("lastTime",dateTemp);
            resp.addCookie(cookie);
            resp.getWriter().write("<h1>您好，欢迎您首次访问</h1>");
        }
    }
}
