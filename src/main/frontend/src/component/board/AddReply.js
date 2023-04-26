import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import { Button , Grid , TextField } from '@mui/material';


export default function AddReply( props ) {

    console.log( props.bno );

    const [ reply , setReply ] = useState({
        rcontent : "",
        bno : props.bno
    });


    const onButtonClick = () => {

        props.addReply( reply );

        setReply( { rcontent : "" , bno : props.bno } );
    }

    const enterKeyEventHandler = (e) => {
        if( e.key === 'Enter' ){
            onButtonClick();
        }
    }

    const onInputChange = (e) => {
        reply.rcontent = e.target.value;
        setReply( {...reply} );
    }


    return (<>
        <Grid container style={{ marginTop : 20 }}>
            <Grid xs={11} md={11} item style={{ paddingRight : 16 }}>
                <TextField
                    placeholder="댓글 입력하기"
                    fullWidth
                    value={ reply.rcontent }
                    onChange={ onInputChange }
                    onKeyPress={ enterKeyEventHandler }
                />
            </Grid>
            <Grid xs={1} md={1} item >
                <Button
                    fullWidth
                    style={{ height:'100%' }}
                    color="secondary"
                    variant="outlined"
                    onClick={ onButtonClick }
                >
                    +
                </Button>
            </Grid>
        </Grid>
    </>);
}