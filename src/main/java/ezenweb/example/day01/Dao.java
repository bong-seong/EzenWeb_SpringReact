package ezenweb.example.day01;

import java.sql.*;

public class Dao {

    private Connection con ;
    private PreparedStatement ps;
    private ResultSet rs;

    public Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/springweb",
                    "root","1234"
            );
            System.out.println("연동성공");
        }catch ( Exception e ){
            System.out.println( "연동실패 : " + e );
        }
    }

    public boolean setMember( LombokDto dto ){
        String sql = "insert into testmember values( ? , ? , ? , ? , ? )";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt( 1, dto.getMno() );
            ps.setString( 2, dto.getMid() );
            ps.setString( 3, dto.getMpwd() );
            ps.setLong( 4, dto.getMpoint() );
            ps.setString( 5, dto.getMphone() );

            ps.executeUpdate();

            return true;

        }catch ( Exception e ){
            System.out.println( e );
        }
        return false;
    }
}
