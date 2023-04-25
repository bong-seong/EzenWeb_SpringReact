import React , { useState , useEffect } from 'react';
import axios from 'axios';

import { Container , Box , TextField , Button } from '@mui/material';

import CategoryList from './CategoryList'

export default function Write( props ) {

    // 카테고리 선택
    let [ cno , setCno ] = useState( 0 );
    const categoryChange = ( cno ) =>{
        setCno( cno );
    }

    // 1. 게시물 쓰기
    const setBoard = () => {
        let info = {
            btitle : document.querySelector('#btitle').value,
            bcontent : document.querySelector('#bcontent').value,
            cno : cno // 선택된 카테고리 번호
        }
        console.log( info );

        axios.post("/board" , info).then( r => {
            if( r.data == 1 ){
                alert("작성할 게시물의 카테고리를 선택해주세요");
            }else if( r.data == 2 ){
                alert("로그인 후 작성 가능합니다.")
            }else if( r.data == 3 ){
                alert("게시물 작성실패 [ 관리자에게 문의 ]")
            }else if( r.data == 4 ){
                alert("작성 성공");
                window.location.href="/board/list"
            }
        })
    }


    return (<>
        <Container>
            <CategoryList categoryChange={ categoryChange } />
            <TextField fullWidth className="btitle" id="btitle" label="제목" variant="standard" />
            <TextField fullWidth className="bcontent" id="bcontent"
                      label="내용"
                      multiline
                      rows={10}
                      variant="standard"
            />
            <Button variant="outlined" onClick={ setBoard }> 등록 </Button>
            <Button variant="outlined"> 취소 </Button>
        </Container>
    </>);
}