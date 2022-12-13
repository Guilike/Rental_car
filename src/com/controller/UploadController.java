package com.controller;

import com.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @User:桂炯
 * @Date:2022-11-24
 * @Time:13:47
 * @Description:
 */
@Controller
@ResponseBody
public class UploadController {
    @PostMapping("/uploadCar")
    public Map<String, Object> addCar(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        // 1.判断文件是否为空
        if (file.isEmpty()) {
            return R.r(null, "请上传文件", 400);
        }
        //获取存储位置
        String realPath = request.getServletContext().getRealPath("/");
//        System.out.println("1:"+realPath);
        String substring = realPath.substring(0, realPath.indexOf("out"));
//        System.out.println("2:"+substring);
        //获取源文件名
        String originalFilename = file.getOriginalFilename();
        //获取拓展名
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        //根据当前时间生成新的文件名
        String fileName = System.currentTimeMillis() + ext;

        String uploadPath = substring + "web\\upload\\car";
//        System.out.println("3:" + uploadPath);
        File filePath = new File(uploadPath, fileName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();   //创建不存在的文件夹
            System.out.println("创建目录" + filePath);
        }
        file.transferTo(filePath);
        return R.r("upload/car/" + fileName, "上传成功", 200);
    }

    @PostMapping("/uploadIcon")
    public Map<String, Object> addIcon(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        // 1.判断文件是否为空
        if (file.isEmpty()) {
            return R.r(null, "请上传文件", 400);
        }

        String realPath = request.getServletContext().getRealPath("/");
        String substring = realPath.substring(0, realPath.indexOf("out"));
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + ext;

        String uploadPath = substring + "web\\upload\\Icon";
        File filePath = new File(uploadPath, fileName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();   //创建不存在的文件夹
            System.out.println("创建目录" + filePath);
        }
        file.transferTo(filePath);
        return R.r("upload/Icon/" + fileName, "上传成功", 200);
    }
}
