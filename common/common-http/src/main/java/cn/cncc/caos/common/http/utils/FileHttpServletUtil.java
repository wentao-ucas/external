package cn.cncc.caos.common.http.utils;

import cn.cncc.caos.common.core.exception.BapParamsException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class FileHttpServletUtil {
  public static void downloadFileCommon(HttpServletResponse response, File file, String fileName, Integer byteBufferSize) throws Exception {
    if (!file.exists()) throw new BapParamsException("file not exist " + file.getAbsolutePath());
    try (FileInputStream fis = new FileInputStream(file)) {
      downloadFileCommon(response, fis, fileName, byteBufferSize);
      log.info("Download the file " + file.getAbsolutePath() + " successfully!");
    }
  }

  public static void downloadFileCommon(HttpServletResponse response, File file, String fileName) throws Exception {
    downloadFileCommon(response, file, fileName, 1024);
  }

  public static void downloadFileCommon(HttpServletResponse response, InputStream inputStream, String fileName, Integer byteBufferSize) throws UnsupportedEncodingException {
    // 配置文件下载
    response.setHeader("content-type", "application/octet-stream");
    response.setContentType("application/octet-stream");
    // 下载文件能正常显示中文
    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
    // 实现文件下载
    byte[] buffer = new byte[byteBufferSize];
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(inputStream);
      OutputStream os = response.getOutputStream();
      int i = bis.read(buffer);
      while (i != -1) {
        os.write(buffer, 0, i);
        i = bis.read(buffer);
      }
    } catch (Exception e) {
      log.error("", e);
    } finally {
      if (bis != null) {
        try {
          bis.close();
        } catch (IOException e) {
          log.error("", e);
        }
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          log.error("", e);
        }
      }
    }
  }
}