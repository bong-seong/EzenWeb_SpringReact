import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import AddReply from './AddReply'
import ReplyContent from './ReplyContent'

import { List , Paper , Container } from '@mui/material';


export default function Reply( props ) {

    const [ replies , setReplies ] = useState( [] );

    const getReplies = () =>{
        axios.get("/reply" , { params : { bno : props.bno } } ).then( (r) => {
            console.log(r);
            setReplies( r.data );
        })
    }

    console.log( replies );

    useEffect( () => {
        console.log( 'ReplyGet useEffect 실행' )
        getReplies();
    } , [])


    const addReply = (reply) => {
       axios.post("/reply" , reply ).then( r => {
           console.log(r);
           if( r.data == 3 ){
                alert("댓글작성 성공")
                setReplies( [...replies , reply] );
           }else{
                alert("회원 기능입니다. 로그인 후 작성하세요")
           }
       })
       getReplies();
    }


    // 리플 삭제
    const deleteReply = ( reply ) => {
        axios.delete("/reply" , { params : reply } ).then( r => {
            console.log(r)
            if( r.data == 3 ){
                alert( '댓글삭제 성공' )
                getReplies();
            }else{
                alert('작성한 본인만 삭제 가능합니다.');
            }
        })
    }




    const replyItem =
        <List>
            {
                replies.map( ( reply ) =>
                    <ReplyContent
                        reply={ reply }
                        deleteReply={ deleteReply }
                    />
                )
            }
        </List>




    return (<>
        <div>
            <AddReply addReply={ addReply } bno={props.bno}/>
        </div>

        <div>
           { replyItem }
        </div>

    </>);
}