package com.dcfs.smartaibank.manager.monitor.web.util.excelutil;

import com.dcfs.smartaibank.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wangjzm
 * @data 2019/08/02 17:36
 * @since 1.0.0
 */
@Slf4j
public final class ExportExcelUtil {
    private ExportExcelUtil() {

    }

    /**
     * excel导出
     *
     * @param response HttpServletResponse
     * @param title    excel标题
     * @param <T>      集合中类的class
     */
    public static <T> void export(InputStream in,
                                  HttpServletResponse response,
                                  String title) {
        ServletOutputStream out = null;
        try {
            String fileName =
                    title + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
            out = writeInputStreamToOutPutStream(response, fileName, in);
        } catch (IOException e) {
            log.error("{}-报表导出异常", title, e);
            throw new BusinessException("报表导出异常", e);
        } finally {
            try {
                in.close();
                out.close();
                out.flush();
            } catch (IOException e) {
                log.error("{}-报表导出数据异常", title, e);
                throw new BusinessException("报表导出异常", e);
            }

        }
    }

    /**
     * 将输入流写入ServletOutputStream输出流
     *
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @param in       输入流
     * @return ServletOutputStream
     * @throws IOException
     */
    public static ServletOutputStream writeInputStreamToOutPutStream(HttpServletResponse response,
                                                                     String fileName,
                                                                     InputStream in) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        int b = -1;
        byte[] buffer = new byte[1024];
        while ((b = in.read(buffer)) != -1) {
            out.write(buffer, 0, b);
        }
        return out;
    }
}
