package ezenweb.example.day01;

public class Ex2 {
    public static void main(String[] args) {

        // 1. builder 패턴을 이용한 객체 생성
        LombokDto dto = LombokDto.builder()
                .mno(1) .mid("qweqwe") .mpwd("1234")
                .mphone("010-1234-5600") .mpoint( 100 )
                .build();
        // 2. dto print
        System.out.println("dto = " + dto);

        // 3. Dao 이용한 db 처리
        Dao dao = new Dao();
        // 4. DB에 저장하기
        boolean result = dao.setMember( dto );
        System.out.println( "result = " + result );

        // 4. JPA 이용한 DB 처리

    }
}
