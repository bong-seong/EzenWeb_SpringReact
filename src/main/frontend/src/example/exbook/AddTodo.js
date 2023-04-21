import React , { useState } from 'react';
import { Button , Grid , TextField } from '@mui/material';
import axios from 'axios';

export default function AddTodo( props ) {

    // 사용자가 입력한 데이터를 저장할 상태변수
    const [ item , setItem ] = useState( { title : "" } );

    // 1. 사용자가 입력한 데이터를 가져오기
    // onChange = { onInputChange }
    // onChange 이벤트속성 : 해당 요소가 해당 이벤트를 발생했을때 이벤트 정보를 반환
    // onChange 이벤트 정보 e를 onInputChange 함수로 매개변수 e 전달
    const onInputChange = ( e ) => {
            // console.log( e ); // 해당 이벤트가 발생했을떄의 이벤트정보 들어있는 객체
            // console.log( e.target ); // 해당 이벤트가 발생된 요소 [ TextField ]
        setItem( { title : e.target.value } ); // 상태변경 : set~~~ : 입력받은 값을 가져와서 상태병수를 수정
        console.log( item );
    }

   // 2. AppTodo 로 부터 전달받은 함수
   const addItem = props.addItem;

   // 3. + 버튼을 클릭했을때
   const onButtonClick = () => {
        /*
       axios.post( "http://192.168.17.34:8080/todo" , item )
                           .then( r => {
                               console.log( r );
                           });
       */

       addItem( item ); // 입력받은 데이터를 AppTodo 컴포넌트 한테 전달받은 함수의 매개변수 대입

       setItem( { "title" : "" } ); // 상태변수 초기화
   }

   // 4. 엔터를 입력했을때
   const enterKeyEventHandler = (e) => {
        if( e.key === 'Enter' ){
            onButtonClick();
        }
   }

   return ( <>
        <Grid container style={{ marginTop : 20 }}>
            <Grid xs={11} md={11} item style={{ paddingRight : 16 }}>
                <TextField
                    placeholder="Add Todo here"
                    fullWidth
                    value={ item.title }
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
   </> );
}