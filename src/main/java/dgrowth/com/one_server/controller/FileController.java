package dgrowth.com.one_server.controller;

import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import dgrowth.com.one_server.data.dto.response.FileResponse;
import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.service.FileService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private MinioService minioService;

    private FileService fileService;

    @PostMapping
    public Response<FileResponse> addFile(@RequestParam("file") MultipartFile file) {
        Response<FileResponse> Response = null;
        try {
            String filePath = fileService.uploadImage(file);
            Response = new Response<>(new FileResponse(filePath));
        } catch (MinioException e) {
            Response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_SAVE_MINIO);
            throw new IllegalStateException();
        } catch (IOException e) {
            Response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_SAVE_IMAGE);
            throw new IllegalStateException();
        }

        return Response;
    }
}
