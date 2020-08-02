package jqhk.ssm.controller;

import jqhk.ssm.Utility;
import jqhk.ssm.model.UserModel;
import jqhk.ssm.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Controller
public class FileUploadController {
    String dir = "avatar";
    UserService userService;

    public FileUploadController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/avatar/upload")
    @ResponseBody
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   HttpServletRequest request) {
        if (file.isEmpty()) {
            return "空文件";
        } else if (file.getSize() > 2097152L) {
            Utility.log("upload file size %s", file.getSize());
            return "文件不允许超过2m";
        } else {
            String fileName = UUID.randomUUID().toString();
            String path = dir + "/" + fileName;
            try (FileOutputStream os = new FileOutputStream(path)) {
                os.write(file.getBytes());
                UserModel u = this.userService.currentUser(request);
                this.userService.changeAvatar(fileName, u.getId());
                return "上传成功";
            } catch (IOException e) {
                String s = String.format("Save file <%s> error <%s>", path, e);
                // throw new RuntimeException(s);
                e.printStackTrace();
                return "上传失败";
            }
        }
    }

    @GetMapping("/avatar/{imageName}")
    public ResponseEntity<byte[]> avatar(@PathVariable String imageName) {
        String path = dir + "/" + imageName;

        byte[] content = {};
        try (FileInputStream is = new FileInputStream(path)) {
            content = is.readAllBytes();
        } catch (IOException e) {
            String s = String.format("Load file <%s> error <%s>", path, e);
        }

        // 找不到
        if (content.length < 1) {
            path = dir + "/" + "avatar.gif";
            try (FileInputStream is = new FileInputStream(path)) {
                content = is.readAllBytes();
            } catch (IOException e) {
                String s = String.format("Load file <%s> error <%s>", path, e);
            }
        }

        ResponseEntity<byte[]> image = ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).
                contentType(MediaType.IMAGE_JPEG).body(content);
        return image;
    }

}
