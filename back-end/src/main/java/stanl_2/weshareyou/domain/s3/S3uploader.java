package stanl_2.weshareyou.domain.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;
import stanl_2.weshareyou.domain.board_image.repository.BoardImageRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Service
@Slf4j
public class S3uploader {

    private final AmazonS3Client amazonS3Client;
    private final BoardImageRepository boardImageRepository;

    @Autowired
    public S3uploader(AmazonS3Client amazonS3Client, BoardImageRepository boardImageRepository) {
        this.amazonS3Client = amazonS3Client;
        this.boardImageRepository = boardImageRepository;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 다중 이미지 업로드를 위한 메소드
    public List<BoardImage> uploadImg(List<MultipartFile> fileList){

        List<BoardImage> imageList = new ArrayList<>();

        for(MultipartFile file: fileList){

            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            try {
                amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
                String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;

                BoardImage boardImage = new BoardImage();
                boardImage.setName(fileName);
                boardImage.setImageUrl(fileUrl);

                imageList.add(boardImage);

            } catch (IOException e) {
                throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }

        return imageList;
    }

    // 단일 이미지 업로드를 위한 메소드
    public String uploadOneImage(MultipartFile file){

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;

            return fileUrl;
        } catch (IOException e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new CommonException(ErrorCode.BAD_REQUEST_IMAGE);
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new CommonException(ErrorCode.BAD_REQUEST_IMAGE);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteImg(String fileName){
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}