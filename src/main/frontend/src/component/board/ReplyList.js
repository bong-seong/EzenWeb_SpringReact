import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';
import AddReply from './AddReply'
import ReplyContent from './ReplyContent'
import style from '../../css/board/reply.css';

import { List , Paper , Container , Button } from '@mui/material';

import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { TreeView , TreeItem } from '@mui/lab';
import { ListItem , ListItemText , InputBase , Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';


export default function ReplyList( props ) {


    // 1. 상위 [ view ] 에게 받은 댓글 리스트

    const [ readOnly , setReadOnly] = useState( true );


    const loginSession = JSON.parse( sessionStorage.getItem("login_token") ) ;


    const turnOffReadOnly = () => { console.log("turnOffReadOnly")
        setReadOnly( false ); // readOnly = true 수정불가능 / false 수정가능
    }

    const turnOnReadOnly = (e) => { console.log("turnOnReadOnly")
        if( e.key == "Enter" ){
            console.log( e );
            console.log( e.target );
            console.log( e.target.value );
            console.log( e.target.id );

            let info = {
                rno : e.target.id,
                rcontent : e.target.value
            }

            setReadOnly( true );
            axios.put( "/board/reply" , info ).then( r => {
                console.log( r.data )
                if( r.data){
                    alert('수정성공');
                }else{
                    alert('작성자만 수정 가능합니다.')
                    window.location.reload();
                }
            });
        }
    }

    const editEventHandler = (e) => { console.log("editEventHandler")
        console.log(e.target.value);
        console.log(e.target.id );
        props.onUpdateRender( e.target.value , e.target.id );
    }


    // 2. 댓글 작성 핸들러
    const onWriteHandler = () => {
        props.onReplyWrite( document.querySelector('.rcontent').value );
        document.querySelector('.rcontent').value = '';
    }

    console.log( props.replyList );

    // 3. 삭제
    const onDeleteHandler = ( e , rno ) => {
        console.log( "삭제 실행" + rno );
        props.onReplyDelete( rno );
    }

    // 대댓글 버튼 클릭 함수
    let rereplyYN = {} ;

    const onReReply = (e , rno ) => {
        console.log("대댓글");
        console.log( e.target.id );
        console.log( rno );

        const className = 'rereply' + rno;
        console.log( className );
        console.log( rereplyYN );

        if( rereplyYN[rno] ){
            document.querySelector('.'+className).style.display = 'none';
            rereplyYN[rno] = false;
        }else{
            document.querySelector('.'+className).style.display = 'block';
            rereplyYN[rno] = true;
        }

    }

    const onReReplyWriteHandler = (e,rno) => {
        console.log( "리리플함수실행")
        console.log( rno );
        props.OnReReplyWrite( document.querySelector('.rercontent'+rno).value , rno );
        document.querySelector('.rercontent'+rno).value = '';
    }


    return (<>
        <input className="rcontent" type="text"/>
        <button className="replybtn" onClick={ onWriteHandler }> 댓글작성 </button>
        <h3> 댓글 목록 </h3>
        {
            props.replyList.map( (r) => {
                if( r.rindex == 0 ){
                    return (<>
                        <ListItem>
                            <ListItemText>
                                <div style={{ display:"flex" , justifyContent:"left" }}>
                                    <InputBase
                                        inputProps={{ readOnly : readOnly }}
                                        onKeyDown = { turnOnReadOnly }
                                        onChange = { editEventHandler }
                                        onClick={ turnOffReadOnly }
                                        type="text"
                                        id={ r.rno }
                                        value={ r.rcontent }
                                    />
                                    <span> 작성자 : {r.mname } </span> <span> 작성일 : {r.rdate } </span>
                                </div>
                                <div className={"rereply"+r.rno} style={{ display:'none' , justifyContent : 'left' }}>
                                    {
                                        props.replyList.map( (o) => {
                                            if( r.rno == o.rindex ){
                                                return (<>
                                                    <div style={{ display : "flex"}}>
                                                        <InputBase
                                                            inputProps={{ readOnly : readOnly }}
                                                            onKeyDown = { turnOnReadOnly }
                                                            onChange = { editEventHandler }
                                                            onClick={ turnOffReadOnly }
                                                            type="text"
                                                            id={ o.rno }
                                                            value={ o.rcontent }
                                                            multiline={ true }
                                                        />
                                                        <span> 작성자 : { o.mname } </span> <span> 작성일 : { o.rdate } </span>
                                                        <button onClick={ (e) => onDeleteHandler( e, o.rno) } > 삭제 </button>
                                                    </div>
                                                </>);
                                            }
                                        })
                                    }
                                    <div>
                                        <input className={"rercontent"+r.rno} type="text" /> <button onClick={ (e) => onReReplyWriteHandler( e, r.rno ) }> 등록 </button>
                                    </div>
                                </div>
                            </ListItemText>
                            <ListItemSecondaryAction>
                                <Button
                                    style={{ height:'100%' }}
                                    color="secondary"
                                    variant="outlined"
                                    id={ r.rno }
                                    onClick={ (e) => onReReply(e , r.rno ) }
                                >
                                    대댓글
                                </Button>
                                <IconButton aria-label="Delete todo" onClick={ (e) => onDeleteHandler( e, r.rno) }>
                                    <DeleteOutlined />
                                </IconButton>
                            </ListItemSecondaryAction>
                        </ListItem>
                    </>);
                }
            })
        }
    </>);
}



