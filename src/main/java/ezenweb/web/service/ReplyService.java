package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    ReplyEntityRepository replyEntityRepository;
    @Autowired
    MemberEntityRepository memberEntityRepository;
    @Autowired
    BoardEntityRepository boardEntityRepository;

    @Transactional
    public byte addReply( ReplyDto replyDto ){

        // 1. 세션에서 로그인한 사람 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( o.equals("anonymousUser") ){
            return 1;
        }
        // 2. 형변환
        MemberDto loginDto = (MemberDto)o;
        // 3. 회원엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() );


        // 게시글 엔티티 찾기
        BoardEntity boardEntity = boardEntityRepository.findById( replyDto.getBno() ).get() ;

        // 리플저장
        ReplyEntity replyEntity = replyEntityRepository.save( replyDto.toEntity() );

        if( replyEntity.getRno() < 1 ){
            return 2;
        }
        // 양방향 관계를 위한 대입 board <-> reply
        boardEntity.getReplyEntityList().add( replyEntity ); // board엔티티에 리플 추가
        replyEntity.setBoardEntity( boardEntity ); // reply엔티티에 board 엔티티 추가

        // 양방향 관계를 위한 대입 member <-> reply
        memberEntity.getReplyEntityList().add( replyEntity );
        replyEntity.setMemberEntity( memberEntity );

        return 3;
    }

    // 댓글 출력
    @Transactional
    public List<ReplyDto> getReply( int bno ){

        List<ReplyEntity> replyEntityList = replyEntityRepository.findBybno( bno );

        List<ReplyDto> list = new ArrayList<>();

        if( replyEntityList != null ){
            replyEntityList.forEach( (o) -> {
                list.add( o.toDto() );
            });
        }

        return list;
    }

    // 댓글 삭제
    @Transactional
    public byte deleteReply( ReplyDto replyDto ){

        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( o.equals("anonymousUser") ) {
            return 1;
        }
        // 2. 형변환
        MemberDto loginDto = (MemberDto)o;
        // 3. 회원엔티티 찾기
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() );


        Optional<ReplyEntity> optionalReplyEntity = replyEntityRepository.findById( replyDto.getRno() );
        if( !optionalReplyEntity.isPresent() ){
            return 2;
        }

        ReplyEntity replyEntity = optionalReplyEntity.get();

        if( loginDto.getMno() == replyEntity.getMemberEntity().getMno() ){
            replyEntityRepository.delete( replyEntity );
            return 3;
        }

        return 0;
    }




}
