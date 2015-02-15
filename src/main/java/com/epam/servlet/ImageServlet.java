package com.epam.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ImageServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = req.getPathInfo().substring(1);

        if (imageName == null) return;

//        imagePath = new String(imagePath.getBytes("ISO-8859-1"),"UTF-8");

        String mimeType = req.getServletContext().getMimeType(imageName);
        if (mimeType == null) {
            resp.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        resp.setContentType(mimeType);
        File file = new File("static/image/" + imageName);

        if (!file.exists()){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        resp.setContentLength((int) file.length());

        FileInputStream in = new FileInputStream(file);
        ServletOutputStream out = resp.getOutputStream();

        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = in.read(buf)) >= 0){
            out.write(buf, 0, length);
        }
        out.close();
        in.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
