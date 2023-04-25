import React , { useState , useEffect } from 'react';
import Todo from './Todo';
import { List , Paper , Container } from '@mui/material';
import AddTodo from './AddTodo';
import axios from 'axios'; // npm install axios vs [ install -> i ]
import Pagination from '@mui/material/Pagination';

// 교재 App 컴포넌트 --> AppTodo 컴포넌트
export default function AppTodo( props ) {

    // 1.
    // item 객체에 { id : "0" , title : "Hellow World 1" , done : true } 대입한것과 같음
    const [ items , setItems ] = useState( [] );

    const [ page , setPage ] = useState( 1 );
    let [ totalPage , setTotalPage ] = useState( 1 );
    let [ totalCount , setTotalCount ] = useState( 1 );

    // 전체 리스트 가져오기
    const getTodo = () => {
        axios.get( "/todo.do" , { params: { 'page' : page } } ).then( r => {
                console.log( r );
                setItems( r.data.todoDtoList );
                setTotalPage( r.data.totalPage );
                setTotalCount( r.data.totalCount );
            }
        );
    }


    // 컴포넌트가 실행될때 한번 요청
    useEffect( () => {

        // ajax : jquery 설치가 필요
        // fetch : 리액트 전용 비동기 통신 함수 [ 내장함수 - 설치 X ]
        // axios : 리액트 외부 라이브러리 [ 설치 필요 ] JSON 통신 기본값
        getTodo();

        /*
            calhost/:1 Access to XMLHttpRequest at
            'http://localhost:8080/todo' from origin 'http://localhost:3000'
            has been blocked by CORS policy: No 'Access-Control-Allow-Origin'
            header is present on the requested resource. ** 에러 발생 **
        */

    } , [ page ] );


    // 2. items에 새로운 item 등록하는 함수
    const addItem = ( item ) => { // 함수로부터 매개변수로 전달받은 item
        axios.post( "/todo.do" , item )
                                   .then( r => {
                                       console.log( r );
                                       getTodo();
                                   });
        setItems( [...items , item] ); // 기존 상태 items 에 item 추가
        // setItems( ...상태명 , 추가할데이터 ] );
    }

    // 3. items 에서 item 삭제
    const deleteItem = ( item ) => {
        console.log( item.id );
        // 만약에 items 에 있는 item 중 id와 삭제할 id 와 같지않으면 해당 item 반환
        const newItems = items.filter( i => i.id !== item.id );
            // * 삭제할 id 를 제외한 새로운 newItems 배열이 선언
        // JS 반복문 함수 제공
            // r = [ 1 , 2 , 3 ]
            // 배열/리스트.forEach() : 반복문 가능 [ return 없음 ]
                // let array = r.forEach( (o) => { o+3 } );
                // 반복문이 끝나도 array에는 아무것도 들어있지않다 .
            // 배열/리스트.map()     : + return 값들을 새로운 배열에 저장
                // let array = r.map( (o) => { return o+3 } );
                // 반복문이 끝나면 array에는 [ 4 , 5 , 6 ]
            // 배열/리스트.filter    : + 조건충족할경우 객체 반환
                // let array = r.filter( (o) => { return 0>=3 } );
                // 반복문이 끝나면 array에는 [ 3 ]

        setItems( [...newItems] );
        axios.delete( "/todo.do" , { params : { id : item.id } } )
            .then( r => {
                console.log( "delete : " + r.data );
                getTodo();
            });
    }

    // 4. 수정함수
    const editItem = () => {
        setItems([...items]) // 재 렌더링
    }

    // 반복문 이용한 Todo 컴포넌트 생성
    // jsx 의 style 속성 방법
    let TodoItems =
        <Paper style={{ margin : 16 }}>
            <List>
                {
                    items.map( ( i ) =>
                        <Todo
                            item={ i }
                            key={ i.id }
                            deleteItem={ deleteItem }
                            editItem={editItem}
                        />
                    )
                }
            </List>
        </Paper>

    const selectPage = (e) => {
        console.log( e.target.outerText );
        setPage( e.target.outerText );
    }


    return ( <>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={addItem} />
                { TodoItems }

                <div style={{ display:'flex' , justifyContent:'center' , margin: '40px 0px' }}>
                    <Pagination count={ totalPage } variant="outlined" color="secondary" onClick={ selectPage }/>
                </div>

            </Container>
        </div>
    </> );
}