package ru.javalab.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.javalab.models.FileModel;
import ru.javalab.services.FileService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@MultipartConfig
@Controller
public class FileController {
    @Autowired
    FileService fileService;

    @RequestMapping(value = "/file/{filename:.+}")
    public @ResponseBody void getFilePage(@PathVariable String filename, HttpServletResponse response){
        FileModel fileModel = fileService.load(filename);

        File file = new File(fileModel.getPath());
        try {
            InputStream inputStream = new FileInputStream(file);
            response.setContentType(fileModel.getType());
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getUploadPage(){
        return "fileupload";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody String uploadFile(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            fileService.upload(file, name, email);
            return "ok";
        } else {
            return "ne ok";
        }
    }
}
