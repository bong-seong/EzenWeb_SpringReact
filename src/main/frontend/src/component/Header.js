import React , { useState , useEffect } from 'react';
import axios from 'axios';
import styles from '../css/header.css';

export default function Header( props ){

    // let [ login , setLogin ] = useState( JSON.parse( localStorage.getItem("login_token") ) );
    let [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem("login_token") ) );

    // 로그아웃
    const logOut = () => {
        sessionStorage.setItem("login_token" , null );

        // 백엔드의 인증세션 지우기
        axios.get("http://localhost:8080/member/logout")
            .then( r => {
                console.log( r );
            })

        window.location.href="/login"
    }

    return (<>
        <div className="header_wrap">
            <div className="menu_box">
                <a href="/"> Home </a>
                <a href="/board/list"> 게시판 </a>
                <a href="/admin/dashboard"> 관리자 </a>
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