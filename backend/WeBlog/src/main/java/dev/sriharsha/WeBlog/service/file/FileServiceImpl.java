package dev.sriharsha.WeBlog.service.file;

import dev.sriharsha.WeBlog.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //get original name of the file
        String fileName = file.getOriginalFilename();

        assert fileName != null;
        if (fileName.isEmpty()) {
            throw new ApiException("Cannot Proceed! Upload Image");
        }
        String fileExtension = fileName.split("\\.")[1];

        if (!(fileExtension.contains("jpg") || fileExtension.contains("png"))) {
            throw new ApiException("File Format Not Supported! Only .jpg and .png files are allowed");
        }
        String fileName1 = UUID.randomUUID().toString().concat(".").concat(fileExtension);

        //get full path of the file
        String fullPath = path + File.separator + fileName1;

        //Create Folder if not created
        File newFile = new File(path);
        if (!newFile.exists()) {
            newFile.mkdir();
        }

        //Copy the file to the folder
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return fileName1;
    }

    @Override
    public InputStream downloadImage(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }
}
