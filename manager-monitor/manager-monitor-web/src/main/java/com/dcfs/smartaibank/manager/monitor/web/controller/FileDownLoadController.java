package com.dcfs.smartaibank.manager.monitor.web.controller;

/**
 * 文件下载服务
 *
 * @author wangjzm
 * @data 2019/08/29 17:38
 * @since 1.0.0
 */

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.config.FileUploadConfig;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.util.excelutil.ExportExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
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
@Api(value = "/api/v1/monitor", description = "监控-文件下载")
@RequestMapping(value = "/api/v1/monitor")
public class FileDownLoadController {
    @Autowired
    FileUploadConfig fileUploadConfig;

    @ApiOperation(value = "文件下载", notes = "文件下载")
    @ApiImplicitParams({
    })
    @GetMapping("/download")
    public void download(
            @RequestParam("fileName") String fileName, HttpServletResponse response) {
        String fileDir = null;
        ServletOutputStream out = null;
        InputStream in = null;
        try {
            if (fileName.endsWith(Constance.PICTURE_TYPE_SUFFIX)) {
                fileDir = fileUploadConfig.getPicUrl();
            }
            if (fileName.endsWith(Constance.ZIP_TYPE_SUFFIX)) {
                fileDir = fileUploadConfig.getLogUrl();
            }
            log.info("截取前缀", fileName.indexOf("PIC") > -1);
            if (fileName.endsWith(Constance.ZIP2_TYPE_SUFFIX)) {
                String fileData;
                String pic = "PIC";
                String device = "DEVICE";
                if (fileName.indexOf(pic) > -1) {
                    fileData = fileName.substring(fileName.length() - 12, fileName.length() - 4);
                    fileDir = fileUploadConfig.getQueuePicUrl() + File.separator + fileData;
                }
                if (fileName.indexOf(device) > -1) {
                    fileData = fileName.substring(fileName.length() - 12, fileName.length() - 4);
                    fileDir = fileUploadConfig.getQueueLogUrl() + File.separator + fileData;
                }
            }
            File excelDir = new File(fileDir);
            if (!excelDir.exists()) {
                excelDir.mkdir();
            }
            String fileFullName = fileDir + File.separator + fileName;
            in = new FileInputStream(new File(fileFullName));
            out = ExportExcelUtil.writeInputStreamToOutPutStream(response, fileName, in);
        } catch (IOException e) {
            log.error("{} 文件下载失败", fileName, e);
            throw new BusinessException("file.download.error", e);
        } finally {
            try {
                in.close();
                out.close();
                out.flush();
            } catch (IOException e) {
                log.error("关闭输入输出流失败", e);
            }
        }
    }

}
