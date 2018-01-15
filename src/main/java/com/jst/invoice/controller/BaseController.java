/*
* Copyright (c) 2015-2018 SHENZHEN JST SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市捷顺金科研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.jst.invoice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.alibaba.fastjson.JSON;
import com.jst.invoice.common.util.FastDfsUtils;
import com.jst.invoice.bean.RequestFile;
import com.jst.invoice.config.ConfigSetting;
import com.jst.prodution.constants.ApiConstants;
import com.jst.prodution.util.ILogger;

public class BaseController {

    private static ILogger log = new ILogger(ApiConstants.JST_PARK, BaseController.class);

    private String fastDfsUrl = ConfigSetting.getProperty("appconfig.fastDfsUrl");

    /**
     * @param request
     * @return
     */
    protected List<RequestFile> getFile(HttpServletRequest request) {
        List<RequestFile> list = getRequestFile(request);
        return list;
    }

    /**
     * 
     * @param request
     * @param fileName
     *            限定文件的字段名称
     * @return
     * @throws Exception
     */
    private List<RequestFile> getRequestFile(HttpServletRequest request) {

        log.info("FastDfs上传文件前----------------------");
        RequestFile requestFile = null;
        List<RequestFile> requestFiles = new ArrayList<RequestFile>();
        MultipartRequest multipartRequest;
        try
        {
            multipartRequest = (MultipartRequest) request;

            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            for (String name : fileMap.keySet())
            {

                log.info("FastDfs上传文件前路径：name = "+ name);
                if (fileMap.get(name).isEmpty())
                {
                    continue;
                }
                else
                {
                    requestFile = new RequestFile();
                    requestFile.setName(name);
                    String prefix = fileMap.get(name).getOriginalFilename().substring(fileMap.get(name)
                            .getOriginalFilename().indexOf("."));
                    log.info("FastDfs上传文件前路径：path = "+ fileMap.get(name).getOriginalFilename()+" , prefix = "+prefix);
                    requestFile.setFileName(fileMap.get(name).getOriginalFilename());
                    requestFile.setFileSize(fileMap.get(name).getSize() + "");
                    requestFile.setFileType(fileMap.get(name).getContentType());
                    String fileurl = fastDfsUrl + FastDfsUtils.upload(fileMap.get(name).getBytes(), requestFile
                            .getFileName(), fileMap.get(name).getSize());
                    log.info("FastDfs上传文件后路径：fileurl = "+ fileurl);
                    log.info("上传文件信息 requestFile = "+ JSON.toJSONString(requestFiles));
                    
                    requestFile.setFileUrl(fileurl);
                    requestFiles.add(requestFile);

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return requestFiles;
        }
        return requestFiles;
    }

    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null)
        {
            if (forwarded == null)
            {
                ip = remoteAddr;
            }
            else
            {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        }
        else
        {
            if (realIp.equals(forwarded))
            {
                ip = realIp;
            }
            else
            {
                if (forwarded != null)
                {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }

        if (ip == null || "".equals(ip))
        {
            ip = "127.0.0.1";
        }
        return ip;
    }

}
