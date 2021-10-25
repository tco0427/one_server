package dgrowth.com.one_server.service;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import dgrowth.com.one_server.data.property.Config;
import java.io.IOException;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class FileService {

    private MinioService minioService;

    public String uploadImage(MultipartFile file) throws IOException, MinioException {
        Path path = Path.of(file.getOriginalFilename());
        // 파일 업로드
        minioService.upload(path, file.getInputStream(), file.getContentType());

        // 파일명, 경로 리턴
        String fileName = path.toString();
        String filePath = Config.MINIO_URL + "/" + fileName;

        return filePath;
    }
}
