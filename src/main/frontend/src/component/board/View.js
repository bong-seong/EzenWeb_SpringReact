import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import Reply from './Reply';
import ReplyList from './ReplyList';

import { Container, List , Paper , Button } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { TreeView , TreeItem } from '@mui/lab';
import { ListItem , ListItemText , InputBase , Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

export default function View( props ) {

    const params = useParams(  );

    console.log( params );
    console.log( params.bno );

    const loginSession = JSON.parse( sessionStorage.getItem("login_token") ) ;
    console.log( loginSession.mno );

    const [ board , setBoard ] = useState ( {
        replyDtoList : []
    } );

    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem("login_token")) );
    const [ trigger , setTrigger ] = useState(0);


    // 1. 현재 게시물 가져오는 axios 함수
    const getBoard = () => {
        axios.get("/board/getboard" , { params : { bno : params.bno } } ).then( r => {
            console.log( r );
            setBoard( r.data );
        })
    }

    useEffect( () => {
        getBoard();
    } , [] );


    const onDelete = () => {
        axios.delete("/board" , { params : { bno : board.bno } } ).then( r => {
            console.log( r.data );
            if( r.data == true ){
                alert( '삭제성공' );
                window.location.href="/board/list";
            }
        })
    }

    // 수정 페이지로 이동
    const onUpdate = () => {
        window.location.href = '/board/update?bno='+board.bno ;
    }

    // 1. 현재 로그인된 회원이 들어왔으면
    const btnBox = () => {
        if( login != null && login.mno == board.mno){
            return ( <> {
                        <div>
                            <button type="button" onClick={ onDelete }> 삭제 </button>
                            <button type="button" onClick={ onUpdate }> 수정 </button>
                        </div>
            } </> ) ;
        }
    }

    const renderTrigger = () => {
        setTrigger( 1 );
    }

    // 2. 댓글 작성시 렌더링
     const onReplyWrite = ( rcontent ) => {
        let info = {
            bno : board.bno,
            rcontent : rcontent
        }
        console.log( info );

        axios.post("/board/reply" , info ).then( (r) => {
            console.log( r.data );
            if( r.data ) {
                alert("글쓰기 완료")
                getBoard();
            }else{
                alert("로그인이 필요한 서비스입니다.");
            }
        })
     }


     // 대댓글 등록
     const OnReReplyWrite = ( rcontent , rindex ) => {

        console.log( rcontent + rindex );

        let info = {
            rindex : rindex,
            rcontent : rcontent,
            bno : board.bno
        }

        axios.post("/board/reply" , info ).then( (r) => {
            console.log( r.data )
            if( r.data == true ){
                getBoard();
            }
        })
     }





     // 3. 댓글 삭제 및 렌더링
     const onReplyDelete = (rno) => {
        console.log( rno );
        axios.delete("/board/reply" , { params : { rno : rno , mno : loginSession.mno }  } ).then( (r) => {
            console.log( r.data );
            if( r.data ){
                alert('삭제완료');
                getBoard();
            }else{
                alert('작성자 본인만 삭제 가능합니다.');
            }
        })
     }

    // 4. 수정

    const onUpdateRender = ( e , rno ) => {
        board.replyDtoList.forEach( (o) => {
            if( o.rno == rno ){
                o.rcontent = e;
                setBoard( {...board} );
            }
        })
    }

    // 5. 대댓글 작성 HTML 전달



    return (<>
        <Container>
            <h3> 게시판 상세 페이지 </h3>
            <h3>{ board.btitle }</h3>
            <p>{ board.bcontent }</p>
            { btnBox() }
            { /*
            <List>
                <ReplyList
                    onUpdateRender={ onUpdateRender }
                    onReplyDelete={ onReplyDelete }
                    onReplyWrite={ onReplyWrite }
                    replyList={ board.replyDtoList }
                    renderTrigger={ renderTrigger }
                    OnReReplyWrite={ OnReReplyWrite }
                    bno={ board.bno }
                />
            </List>
            */ }

            <Reply bno={params.bno} />

        </Container>
    </>);
}

/*
    // useParams() 훅 : 경로[URL]상의 매개변수[객체] 반환
    // http://localhost:8080/board/view/25
    // http://localhost:8080/board/view/:bno ----> useParams(); ----> { bno : 25 }

    // http://localhost:8080/board/view/25/안녕하세요
    // http://localhost:8080/board/view/:bno/:comment ----> useParams(); ----> { bno : 25 , comment : 안녕하세요 }
*/