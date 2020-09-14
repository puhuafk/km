package com.stylefeng.guns.modular.upload;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.FileUtils;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.BusinessException;
import com.stylefeng.guns.core.util.IpUtil;
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
public class FileUploadController extends BaseController {

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

    @Value("${web.image-mapping}")
    private String imageMapping;

    @Value("${web.ip}")
    private String ip;

    @Value("${server.port}")
    private String port;


    @PostMapping(value = "/upload/image")
    @ResponseBody
    public Object add(@RequestParam("imageFile") MultipartFile multipartFile) {

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

        String serverPath = "http://" + ip+":" + port+"/"+imageMapping+"/";  //外网地址

//        String serverPath = "http://" + IpUtil.INTERNET_IP+":" + port+"/"+uploadMapping+"/";  //内网定制

        return serverPath+newFileName;
    }

    public static void main(String[] args) {

        System.out.println(IpUtil.INTERNET_IP);

    }

}
