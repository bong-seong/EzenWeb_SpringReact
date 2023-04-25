import React , { useState , useEffect } from 'react';
import axios from 'axios';

import { Container , Box , TextField , Button } from '@mui/material';

export default function Signup(props) {

    let [ memailMsg , setMemailMsg ] = useState( {} );
    let [ mphoneMsg , setMphoneMsg ] = useState( {} );

    // 2. 아이디 중복체크
    const idCheck = (e) => {
        // 1. console.log( document.querySelector('.memail').value );
        // 2. console.log( e.target.value )
        axios.get("/member/idcheck" , { params : { memail : e.target.value } } )
            .then( r => {
                console.log( r );
                if( r.data == true ){
                    setMemailMsg( { msg : '사용중인 아이디 입니다.' , yn : false } );
                }else{
                    setMemailMsg( { msg : '사용 가능한 이메일입니다.' , yn : true } );
                }
            })
            .catch( err => { console.log(err) } );
    }

    // 3. 전화번호 중복체크
    const phoneCheck = (e) => {
        // 1. console.log( document.querySelector('.memail').value );
        // 2. console.log( e.target.value )
        axios.get("/member/phonecheck" , { params : { mphone : e.target.value } } )
            .then( r => {
                console.log( r );
                if( r.data == true ){
                    setMphoneMsg( { msg : '사용중인 전화번호 입니다.' , yn : false } );
                }else{
                    setMphoneMsg( { msg : '사용 가능한 전화번호입니다.' , yn : true } );
                }
            })
        .catch( err => { console.log(err) } );
    }

    // 1. 회원가입
    // function onSignup() --> 변수형 익명함수 변환
    // function onSignup() --> const 변수 = () => {}
    const onSignup = () =>{
        console.log(  "yn : " + memailMsg.yn )

        if( memailMsg.yn && mphoneMsg.yn ) {

            console.log("onSignup")

            let info ={
                memail : document.querySelector('.memail').value,
                mname : document.querySelector('.mname').value,
                mpassword : document.querySelector('.mpassword').value,
                mphone : document.querySelector('.mphone').value
            }

            console.log( info )

            // ajax --> axios 변환
            axios.post("/member/info" , info )
                .then( r => {
                    console.log( r);
                    if( r.data == true ) {
                        alert( '가입완료' )
                        window.location.href="/member/login";
                    }
                    else alert( '중복되는 아이디 입니다.');
                    // window.location.href = "이동할 경로"
                })
                .catch( err => { console.log(err) });
        }else{
            alert('아이디/전화번호를 다시한번 확인바랍니다.')
        }
    }

    return(<>
        <Container>
            <h3> 회원가입 페이지 </h3>
              <form>
                  <table>
                        <tbody>
                              <tr>
                                <td>아이디[이메일] : </td> <td> <input type="text" name="memail" className="memail" onChange={ idCheck }/> </td>
                                <span> { memailMsg.msg } </span>
                              </tr>
                              <tr>
                                  <td> 이름 : </td> <td> <input type="text" name="mname" className="mname" /> </td>
                              </tr>
                              <tr>
                                  <td> 비밀번호 :  </td><td> <input type="password" name="mpassword" className="mpassword" /> </td>
                              </tr>
                              <tr>
                                  <td> 전화번호 :  </td> <td> <input type="text" name="mphone" className="mphone" onChange={ phoneCheck } /> </td>
                                  <span> { mphoneMsg.msg } </span>
                              </tr>
                              <tr>
                                  <td colSpan="2"> <button type="button" onClick={ onSignup }> 가입 </button> </td>
                              </tr>
                        </tbody>
                  </table>
              </form>
        </Container>
    </>);
}

/*
    HTML ---> JSX
    1. <마크업> </마크업>
    2. class -> className
    3. style -> style={{ }}
    4. 카멜표기법 :
    onclick -> onClick
*/


