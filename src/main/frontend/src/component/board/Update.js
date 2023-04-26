import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';
import CategoryList from './CategoryList'
import { Container , Box , TextField , Button } from '@mui/material';

import { useSearchParams } from 'react-router-dom'; // 현재 URL 의 쿼리스트링 반환 [ 객체 ]

export default function Update( props ) {

    const [ searchParams , setSearchParams ] = useSearchParams() ;
    const [ board , setBoard ] = useState({});
    const [ cno , setCno ] = useState( 0 )

    console.log( searchParams );
    console.log( searchParams.get("bno") );


    useEffect( () => {
        axios.get("/board/getboard" , { params : { bno : searchParams.get("bno") } } ).then( r => {
            console.log( r );
            setBoard( r.data );
            setCno( r.data.cno );
        })
    } , [] );


    const onUpdate = () => {
        axios.put("/board" , board ).then( r => {
            console.log( r );
            if( r.data ){
                alert('수정성공');
                window.location.href = '/board/view/'+searchParams.get("bno");
            }else{
                alert('수정실패');
            }
        })
    }

    const categoryChange = (cno) => {
        setCno( cno );
    }

    const backSpace = () => {
        window.location.href = '/board/view/'+searchParams.get("bno");
    }

    // 입력이벤트
    const inputTitle = (e) => {
        console.log( e.target.value);
        board.btitle = e.target.value;
        setBoard( {...board} );
    }
    const inputContent = (e) => {
        console.log( e.target.value);
        board.bcontent = e.target.value;
        setBoard( {...board} );
    }


    return (<>
        <Container>
            <h3> 수정페이지 </h3>

            <CategoryList categoryChange={ categoryChange }/>
            <TextField fullWidth value={ board.btitle } onChange={ inputTitle } className="btitle" id="btitle" variant="standard" />
            <TextField fullWidth value={ board.bcontent } onChange={ inputContent } className="bcontent" id="bcontent"
                      multiline
                      rows={10}
                      variant="standard"
            />
            <Button variant="outlined" onClick={ onUpdate }> 등록 </Button>
            <Button variant="outlined" onClick={ backSpace }> 취소 </Button>
        </Container>
    </>);

}


