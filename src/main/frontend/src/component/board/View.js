import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import Reply from './Reply';

import { Container } from '@mui/material';

export default function View( props ) {

    const params = useParams(  );

    console.log( params );
    console.log( params.bno );

    const [ board , setBoard ] = useState ( {} );
    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem("login_token")) );

    useEffect( () => {
        axios.get("/board/getboard" , { params : { bno : params.bno } } ).then( r => {
            console.log( r );
            setBoard( r.data );
        })
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


    return (<>
        <Container>
            <h3> 게시판 상세 페이지 </h3>
            <h3>{ board.btitle }</h3>
            <p>{ board.bcontent }</p>
            { btnBox() }

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