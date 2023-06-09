import React , { useState , useEffect } from 'react';
import axios from 'axios';
import styles from '../../css/member/login.css';
import { Container , Box , TextField , Button } from '@mui/material';

export default function Login( props ) {

    // 일반회원 로그인
    const onLogin = () =>{

        console.log( 'onLogin 실행' )

        let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData( loginForm );

        axios.post("/member/login", loginFormData )
            .then( r => {
                console.log( r );
                if( r.data == false) {
                    alert( '동일한 회원정보가 존재하지 않습니다.' );
                }else{
                    alert('로그인성공')
                    // JS 로컬 스토리지[ 브라우저 모두 닫혀도 사라지지 않는다. , 도메인마다 따로 저장 된다 ] 에 로그인 성공한 흔적 남기기
                    // localStorage.setItem("key", value ) // key · value : String 타입
                    // value 에 객체 대입시 [Object] ????? 객체처럼 사용 불가
                    // JSON.stringfy( 객체 ) : 해당 객체를 String 타입의 json 형식으로 변환
                    // 다시 꺼내서 사용할때는 JSON.parse( 객체 )
                    // localStorage.setItem("login_token" , JSON.stringify( r.data ) );
                    // JS 세션스토리지 [ 브라우저 모두 닫히면 사라진다. ]
                    // sessionStorage.setItem("login_token" , JSON.stringify( r.data ) );
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
            <TextField fullWidth className="mname" id="mname" label="이름" variant="standard" />
            <TextField fullWidth className="mname" id="mname" label="이름" variant="standard" />
            <table className="loginbox">
                <tbody className="loginbox_body">
                    <tr>
                        <td>아이디[이메일] : </td> <td> <input type="text" name="memail" className="memail" /> </td>
                    </tr>

                    <tr>
                        <td> 비밀번호 :  </td><td> <input type="password" name="mpassword" className="mpassword" /> </td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/member/find"> 아아디 / 비밀번호 찾기 </a> </td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <button className="loginbtn" type="button" onClick={ onLogin }> 로그인 </button> </td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/oauth2/authorization/google" className="googole_login"> 구글로그인 </a></td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/oauth2/authorization/kakao" classNAme="kakao_login"> 카카오로그인 </a></td>
                    </tr>
                    <tr>
                        <td colSpan="2"> <a href="/oauth2/authorization/naver" className="naver_login"> 네이버로그인 </a></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </>);
}