package ezenweb.example.day01;

public class Ex1 {
    public static void main(String[] args) {

        // 1. dto


        // 2. lombok [ @NoArgsConstructor ]
        LombokDto lombokDto1 = new LombokDto();
        // 2. lombok [ @AllArgsConstructor ]
        LombokDto lombokDto2 = new LombokDto( 1, "qwe" , "qwe" , 100 , "010-1111-2222");
        // 2. lombok [ @Setter@Getter ]
        System.out.println( "getter : " + lombokDto1.getMid() );
        lombokDto1.setMid("asdf");

        // 2. lombok [ @ToString ]
        System.out.println( "toString() : " + lombokDto1.toString() );

        // 2. lombok [ @Builder ] *****
        LombokDto lombokDto3 = LombokDto.builder()
                .mid("qwe")
                .mpwd("qweqwe").build();
        // 생성자 안쓰고 아이디와 패스워드가 저장된 객체 생성
        // 1. 객체 생성시 매개변수 개수상관 X, 순서상관 X
    }
}
