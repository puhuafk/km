package com.stylefeng.guns.modular.test;


import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.FileUtils;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@Slf4j
public class TestFileUploadController extends BaseController  {

    private static InetAddress addr;


    static {
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Value("${web.upload-path}")
    private String uploadPath;

    @Value("${server.port}")
    private String port;


    @PostMapping(value = "/test/upload")
    @ResponseBody
    public Object add(@RequestParam("imageFile") MultipartFile multipartFile) {

        String ip=addr.getHostAddress();//获得本机IP
        String fileName = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new BusinessException(BizExceptionEnum.IMG_NOT_EMPTY);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new BusinessException(BizExceptionEnum.IMG_FORMAT_ERROR);
        }
        log.info("上传图片:name={},type={}", fileName, contentType);
        String newFileName = FileUtils.getFileName(fileName);
        FileUtils.upload(multipartFile, uploadPath, newFileName);//返回图片上传名称

        String serverPath = "http://" + addr.getHostAddress()+":" + port+"/"+"uploadImage/";

        return serverPath+newFileName;
    }

    public static void main(String[] args) {
        String ip=addr.getHostAddress();//获得本机IP
        System.out.println(ip);

    }

}
