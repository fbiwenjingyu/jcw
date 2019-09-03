package com.ovit.jcw.controller.monitoringobject;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import com.ovit.jcw.utils.*;
import java.text.*;
import java.util.*;
import java.math.*;
import com.ovit.jcw.common.*;
import java.net.*;
import java.io.*;

@RestController
@RequestMapping({ "/fileQuery" })
public class FileController
{
    private Logger logger;
    @Value("${word.path}")
    private String path;
    
    public FileController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/downDefaultExcel" })
    public void downDefaultExcel(final HttpServletRequest request, final HttpServletResponse response) {
        this.logger.info("调查表-导出excel模板");
        final Result result = new Result();
        final Boolean flag = true;
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            final String descPretty = "";
            try {
                final String fileName = "监察对象调查库个人情况调查表.xlsx";
                final String downPath = this.path + "/" + fileName;
                response.setContentType("application/octet-stream");
                response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                final InputStream is = new FileInputStream(downPath);
                int len = 0;
                final byte[] b = new byte[1024];
                final OutputStream os = (OutputStream)response.getOutputStream();
                while ((len = is.read(b)) != -1) {
                    os.write(b, 0, len);
                }
                os.flush();
                is.close();
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                dataUsage = BigInteger.valueOf(0L);
            }
        }
    }
}
