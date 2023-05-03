import React , { useState , useEffect , useRef } from 'react';
import { List , Paper , Container , Button } from '@mui/material';
import styles from '../../css/board/Chatting.css';

export default function Chatting( props ) {

    let [ id , setId ] = useState(''); // 익명채팅방에서 사용할 id [ 난수 저장 ]
    let [ msgContent , setMsgContent ] = useState( [] ); // 현재 채팅중인 메시지를 저장하는 변수

    console.log( msgContent );

    let msgInput = useRef(null); // 채팅입력창[ input ] DOM 객체 제어

    // 1. 재렌더링 될때마다 새로운 접속
    // let clientSocket = new WebSocket("ws/localhost:8080/chat");

    // 2. 재렌더링 될때 데이터 상태 유지
    let ws = useRef( null );        // 1. 모든 함수 사용할 클라이언트 소켓 변수
    useEffect( () => {              // 2. 재 렌더링시 1번만 실행
        if( !ws.current ){          // 3. 클라이언트 소켓에 접속이 안되어있을때 [ * 유효성검사 ]

            ws.current = new WebSocket("ws://localhost:8080/chat"); // 4. 서버소켓 연결

            // 3. 클라이언트소켓이 서버소켓에 접속했을때 이벤트
            ws.current.onopen = () => {
                console.log("접속했습니다.");
                let randId = Math.floor( Math.random() * (9999 - 1) + 1);
                setId( '익명 ' + randId );
            }

            // 3. 서버소켓에서 나갔을때 이벤트
            ws.current.onclose = (e) => {
                console.log("나갔습니다.");
            }

            // 3. 서버소켓과 오류가 발생했을때 이벤트
            ws.current.onerror = (e) => {
                console.log("에러났습니다.");
            }

            // 3. 서버소켓으로부터 메시지를 받았을때 이벤트
            ws.current.onmessage = (e) => {
                console.log("서버소켓으로부터 메시지 받음.");
                console.log( e ); console.log( e.data );
                // msgContent.push( e.data );
                let data = JSON.parse( e.data );
                setMsgContent( (msgContent) => [ ...msgContent , data ] ); // 재 렌더링
            }
        }
    })

    // 4. 메시지 전송
    const onSend = () => {
        console.log( msgInput );
        console.log( msgInput.current.value );
        // msgInput 변수가 참조중인  <input ref={ msgInput } > 해당 input 를 DOM 객체로 호출

        let time = new Date().toLocaleTimeString();

        let msgBox = {
            id : id,                            // 보낸사람
            msg : msgInput.current.value,       // 보낸 내용
            time : time                         // 현재 시간만
        }
        ws.current.send( JSON.stringify( msgBox ) ); // 클라이언트 메시지 전송 [ .send() ]
        msgInput.current.value = '' ;
    }

    // 5. 렌더링 할때마다 스크롤 가장 하단으로 내리기
    useEffect( () => {
        document.querySelector('.chatContentBox').scrollTop = document.querySelector('.chatContentBox').scrollHeight;
    },[msgContent])


    return (<>
        <Container className="Container">
            <div className="chatContentBox">
            {
                msgContent.map( (m) => {
                    return (<>


                        <div className="chatContent" style={ m.id == id ? { backgroundColor: '#e4ebea' , textAlign : 'right' } : { } }>
                            <span> { m.id } </span>
                            <span> { m.msg } </span>
                            <span> { m.time } </span>
                        </div>
                    </>)
                })
            }
            </div>
            <div className="chatInputBox">
                <span> { id } </span>
                <input className="msgInput" ref={ msgInput } type="text" />
                <button onClick={ onSend }> 전송 </button>
            </div>
        </Container>
    </>)
}

/*

    let 숫자 = 10;                            // 지역변수 : 컴포넌트[함수] 호출될때마다 초기화
    let 숫자2 = useRef(10);                   // 재렌더링시 초기값이 적용되지 않는 함수 [ 반환값 : 객체{ current } ]
    let inputRef = useRef(null);             // documet.querySelector vs useRef

    const [ id , setId ] = useState('');     // set메소드 사용시 컴포넌트 재호출 [ 재렌더링 ]

    // 1. 웹소켓 = webSocket = JS
    let webSocket = useRef( null );

    console.log( 숫자 );
    console.log( 숫자2 );
    console.log( id );

    // 2. 난수 생성
    let randId = Math.floor( Math.random() * (9999 - 1) + 1);
        // Math.floor( Math.random() * ( 최댓값 - 최솟값 ) + 최소값 ); : 정수 1~1000
    숫자 = randId;
    숫자2.current = randId;

    // setId( randId );

    const onSend = () => {
        console.log( inputRef.current.value );
        console.log( document.querySelector('.msgInput').value );
    }

 */