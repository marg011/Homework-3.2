package ru.hogwarts.school.service;

import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatars.dir.path}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException{
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try(InputStream is = file.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generatedImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long studentId){
        return avatarRepository.findByAvatarId(studentId).orElse(new Avatar());
    }

    private byte[] generatedImagePreview(Path filePath) throws IOException {
        try(InputStream is = Files.newInputStream(filePath);
        BufferedInputStream bis = new BufferedInputStream(is, 1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight()/(image.getWidth()/100);
            BufferedImage data = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = data.createGraphics();
            graphics.drawImage(image, 0,0,100,height, null);
            graphics.dispose();

            ImageIO.write(data, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();

        }
    }

    private String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}