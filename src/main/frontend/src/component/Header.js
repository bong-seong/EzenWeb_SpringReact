import React from 'react';
import styles from '../css/header.css';

export default function Header( props ){
    return (<>
        <div className="header_wrap">
            <div className="menu_box">
                <a href="/"> 홈 </a>
                <a href="login"> 로그인 </a>
                <a href="signup"> 회원가입 </a>
            </div>
        </div>
    </>);
}