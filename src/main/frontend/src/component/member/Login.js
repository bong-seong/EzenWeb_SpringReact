import React , { useState , useEffect } from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css';

export default function Login( props ) {

    // 로그인
    const onLogin = () =>{

        console.log( 'onLogin 실행' )

        let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData( loginForm );

        axios.post("http://localhost:8080/member/login", loginFormData )
            .then( r => {
                console.log( r );
                if( r.data == false) {
                    alert( '동일한 회원정보가 존재하지 않습니다.' );
                }else{
                    alert('로그인성공')
                    // JS 로컬 스토리지에 로그인 성공한 흔적 남기기
                    window.location.href="/";
                }
            })

        /*
        $.ajax({
            url: "/member/login",
            method: "POST",
            data: loginFormData,
            contentType: false,
            processData: false,
            success: ( r ) => {
                console.log( r );
            }
        })
        */
    }


    return (<>
        <h3> 로그인 페이지 입니다. </h3>
        <form className="loginForm">
            <table>
                <tbody>
                    <tr>
                        <td>아이디[이메일] : </td> <td> <input type="text" name="memail" className="memail" /> </td>
                    </tr>

                    <tr>
                        <td> 비밀번호 :  </td><td> <input type="password" name="mpassword" className="mpassword" /> </td>
                    </tr>

                    <tr>
                        <td colSpan="2"> <button type="button" onClick={ onLogin }> 로그인 </button> </td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/oauth2/authorization/google"> 구글로그인 </a></td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/oauth2/authorization/kakao"> 카카오로그인 </a></td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/oauth2/authorization/naver"> 네이버로그인 </a></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </>);
}