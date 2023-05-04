package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    // * 첨부파일이 저장 될 경로 [ 1. 배포전 , 2. 배포 후 ]
    String path = "C:\\java\\" ;

    public FileDto fileUpload( MultipartFile multipartFile ) {
        log.info("Uploading file : " + multipartFile );
        log.info("Uploading filename : " + multipartFile.getOriginalFilename() );
        log.info("Uploading name : " + multipartFile.getName() );
        log.info("Uploading ContentType : " + multipartFile.getContentType() );
        log.info("Uploading Resource : " + multipartFile.getResource() );
        log.info("Uploading Size : " + multipartFile.getSize() );

        // 1. 첨부파일이 존재하는지 확인
        if( !multipartFile.getOriginalFilename().equals("") ){ // 첨부파일이 존재하면

            // * 만약에 다른 이미지인데 파일명이 동일하면 중복발생 [ 식별 불가 ]
            String fileName = UUID.randomUUID().toString() + "_" +multipartFile.getOriginalFilename();

            // 2. 경로 + UUID파일명 조합해서 file 클래스 생성 [ 왜? 파일?? ]
            File file = new File( path + fileName );

            // 3. 업로드 // multipartFile.transferTo( 저장할 File 클래스의 객체 );
            try{
                multipartFile.transferTo( file );
            }catch ( Exception e ){
                log.info("file upload failed : " + e );
            }

            return FileDto.builder()
                    .originalFilename( multipartFile.getOriginalFilename())
                    .uuidFile( fileName )
                    .sizeKb( multipartFile.getSize()/1024 + "kb" )
                    .build();
        }

        return null;
    }

    public void fileDownload( String filePath ) {

    }

}
