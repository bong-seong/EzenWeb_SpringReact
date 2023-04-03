package ezenweb.example.day01;

import lombok.*;

@Setter@Getter
@AllArgsConstructor @NoArgsConstructor
@ToString
@Builder
public class LombokDto {
    private int mno;
    private String mid;
    private String mpwd;
    private long mpoint;
    private String mphone;

    // dto -> Entity
    public Testmember2 toEntity(){
        return Testmember2.builder()
                .mno( this.mno )
                .mid( this.mid )
                .mpwd( this.mpwd )
                .mpoint( this.mpoint )
                .mphone( this.mphone )
                .build();
    }
}
