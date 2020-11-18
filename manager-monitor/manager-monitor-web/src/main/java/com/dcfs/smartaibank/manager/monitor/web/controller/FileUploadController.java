package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.config.FileUploadConfig;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传控制器
 *
 * @author wangjzm
 * @data 2019/08/29 09:45
 * @since 1.0.0
 */
@Slf4j
@Controller
@Api(value = "/api/v1/monitor", description = "监控-文件上传")
@RequestMapping(value = "/api/v1/monitor")
public class FileUploadController {

    @Autowired
    FileUploadConfig fileUploadConfig;

    @ApiOperation(value = "文件上传服务", notes = "文件上传服务")
    @ApiImplicitParams({
    })
    @PostMapping(value = "/upload/*")
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求路径
        String pathInfo = request.getServletPath();
        String fileName = pathInfo.replaceFirst("/api/v1/monitor/upload/", "");
        String filePath = null;
        String fileType = request.getHeader("Content-type");
        if (Constance.PICTURE_TYPE.equals(fileType)) {
            //截屏图片路径
            filePath = fileUploadConfig.getPicUrl();
        }
        if (Constance.ZIP_TYPE.equals(fileType)) {
            //日志抓取路径
            filePath = fileUploadConfig.getLogUrl();
        }
        File excelDir = new File(filePath);
        if (!excelDir.exists()) {
            excelDir.mkdir();
        }
        File file = new File(filePath + File.separator + fileName);
        boolean result = readFile(request, file);
        if (result) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            throw new BusinessException("file.upload.error");
        }
    }

    /**
     * 保存文件到本地
     *
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    private boolean readFile(HttpServletRequest request, File file) {
        long beginTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("正在上传文件：" + file.getName());
            log.debug("beginTime=" + beginTime);
        }
        // 创建文件
        File rootFile = file.getParentFile();
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        // 保存附件到本地文件
        FileOutputStream fOut = null;
        InputStream in = null;
        try {
            fOut = new FileOutputStream(file);
            in = request.getInputStream();
            byte[] buffer = new byte[1024 * 8];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                fOut.write(buffer, 0, len);
                fOut.flush();
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }
        } finally {
            try {
                if (fOut != null) {
                    fOut.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("endTime=" + endTime + ",花费时间:" + (endTime - beginTime));
        }
        return true;
    }
}
