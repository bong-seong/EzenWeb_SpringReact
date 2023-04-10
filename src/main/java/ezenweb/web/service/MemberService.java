package ezenweb.web.service;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service // 서비스 레이어
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    // 1. 회원가입
    @Transactional
    public boolean write( MemberDto memberDto ){

        MemberEntity entity = memberEntityRepository.save( memberDto.toEntity() );

        if( entity.getMno() > 0 ){
            return true;
        }

        return false;
    }

    // 2. 회원정보
    @Transactional
    public MemberDto info( int mno ){

        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( mno );

        if( entityOptional.isPresent() ){
            MemberEntity entity =  entityOptional.get();
            return entity.toDto();
        }

        return null;
    }

    // 3. 회원수정
    @Transactional
    public boolean update( MemberDto memberDto ){

        Optional<MemberEntity> entityOptional = memberEntityRepository.findById( memberDto.getMno() );

        if( entityOptional.isPresent() ){

            MemberEntity entity = entityOptional.get();
            entity.setMname( memberDto.getMname() );
            entity.setMphone( memberDto.getMphone() );
            entity.setMrole( memberDto.getMrole() );
            entity.setMpassword( memberDto.getMpassword() );

            return true;
        }
        return false;
    }

    // 4. 회원탈퇴
    @Transactional
    public boolean delete( int mno){

        Optional<MemberEntity> entityOptional =
            memberEntityRepository.findById( mno );

        if( entityOptional.isPresent() ){
            memberEntityRepository.delete( entityOptional.get() );
            return true;
        }

        return false;
    }

}

