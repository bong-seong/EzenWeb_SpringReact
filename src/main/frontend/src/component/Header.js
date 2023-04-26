import React , { useState , useEffect } from 'react';
import axios from 'axios';
import styles from '../css/header.css';

export default function Header( props ){

    // let [ login , setLogin ] = useState( JSON.parse( localStorage.getItem("login_token") ) );
    // 로그인 상태변수
    let [ login , setLogin ] = useState( null );

    // 로그아웃
    const logOut = () => {
        // JS 세션 스토리지 초기화
        sessionStorage.setItem("login_token" , null );

        // 백엔드의 인증세션 지우기
        axios.get("/member/logout")
            .then( r => {
                console.log( r );
            })
        setLogin( null ); // 렌더링
    }

    // 로그인 상태 호출
    useEffect( () => {
        axios.get("/member/info").then( r => {
            console.log( r );
            if( r.data != '' ){ // 로그인이 되어있으면 // 서비스에서 null 이면 js 에서 '' 이다.
                // JS 로컬 스토리지에 저장
                sessionStorage.setItem("login_token" , JSON.stringify( r.data ) );
                // 상태변수에 세션스토리지를 호출해서 상태변수에 데이터저장 [ 렌더링 하기 위해 ]
                setLogin( JSON.parse( sessionStorage.getItem("login_token") ) );
            }
        })
    } , [] );



    return (<>
        <div className="header_wrap">
            <div className="menu_box">
                <a href="/"> Home </a>
                <a href="/board/list"> 게시판 </a>
                <a href="/admin/dashboard"> 관리자 </a>
                <a href="/todo"> Todo </a>
                { login == null
                    ? ( <>
                        <a href="/member/login"> 로그인 </a>
                        <a href="/member/signup"> 회원가입 </a>
                    </> )
                    : ( <>
                        <button onClick={ logOut }> 로그아웃 </button>
                    </> )
                }
            </div>
        </div>
    </>);
}