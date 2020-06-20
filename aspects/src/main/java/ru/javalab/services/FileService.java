package ru.javalab.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.javalab.models.FileModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface FileService {

    String  upload(MultipartFile file, String filename, String email) throws IOException;

    Stream<Path> loadAll();

    FileModel load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
