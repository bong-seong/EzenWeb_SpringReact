package ezenweb.web.service;

import ezenweb.web.domain.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
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

    @Autowired private HttpServletResponse response; // 응답 객체

    public void fileDownload( String uuidFile ) { // spring 다운로드 관한 API 없음

        String pathFile = path + uuidFile; // 경로 + uuid 파일명 : 실제 파일이 존재하는 위치
        try{
            // 1. 다운로드 형식 구성
            // 만약에 실제파일명에 _ ( 언더바 ) 가 있을 경우 대비
            String[] parts = uuidFile.split("_");
            String newFilename = String.join("_", Arrays.copyOfRange(parts, 1, parts.length) );

            response.setHeader(
                    "Content-Disposition" ,     // 헤더 구성 [ 브라우저 다운로드 형식 ]
                    "attachment;filename = " + URLEncoder.encode( newFilename , "UTF-8") // 다운로드시 표시할 이름
            );
            // 2. 다운로드 스트림 구성
            File file = new File( pathFile ); // 다운로드할 파일을 경로에서 파일 객체화

            // 3. 입력 스트림 [ 서버가 먼저 다운로드 할 파일의 바이트 읽어오기 = 대상 : 클라이언트가 요청한 파일 ]
            BufferedInputStream fin = new BufferedInputStream( new FileInputStream( file ) );
            byte[] bytes = new byte[ (int)file.length() ]; // 파일길이[용량] 만큼 바이트 배열 선언
            fin.read( bytes ); // 읽어온 바이트들을 bytes 배열 저장

            // 4. 출력 스트림 [ 서버가 읽어온 바이트를 출력할 스트림 = 대상 : response = 현재 서비스를 요청한 클라이언트에게 ]
            BufferedOutputStream fout = new BufferedOutputStream( response.getOutputStream() );
            fout.write( bytes ); // 입력 스트림에서 읽어온 바이트배열을 내보내기
            fout.flush(); // 스트림 메모리 초기화
            fin.close(); fout.close(); // 스트림 닫기

        }catch (Exception e){
            System.out.println( e );
        }


    }

}
