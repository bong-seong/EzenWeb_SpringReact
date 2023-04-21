import React , { useState , useEffect } from 'react';
import axios from 'axios';

export default function Signup(props) {

    // 1. 회원가입
    // function onSignup() --> 변수형 익명함수 변환
    // function onSignup() --> const 변수 = () => {}
    const onSignup = () =>{

        console.log("onSignup")

        let info ={
            memail : document.querySelector('.memail').value,
            mname : document.querySelector('.mname').value,
            mpassword : document.querySelector('.mpassword').value,
            mphone : document.querySelector('.mphone').value
        }

        console.log( info )

        // ajax --> axios 변환
        axios.post("http://localhost:8080/member/info" , info )
            .then( r => {
                console.log( r);
                if( r.data == true ) {
                    alert( '가입완료' )
                    window.location.href="/login";
                }
                else alert( '중복되는 아이디 입니다.');
                // window.location.href = "이동할 경로"
            })
            .catch( err => { console.log(err) });
    }

    return(<>
        <h3> 회원가입 페이지 </h3>
          <form>
              <table>
                    <tbody>
                          <tr>
                            <td>아이디[이메일] : </td> <td> <input type="text" name="memail" className="memail" /> </td>
                          </tr>
                          <tr>
                              <td> 이름 : </td> <td> <input type="text" name="mname" className="mname" /> </td>
                          </tr>
                          <tr>
                              <td> 비밀번호 :  </td><td> <input type="password" name="mpassword" className="mpassword" /> </td>
                          </tr>
                          <tr>
                              <td> 전화번호 :  </td> <td> <input type="text" name="mphone" className="mphone" /> </td>
                          </tr>
                          <tr>
                              <td colSpan="2"> <button type="button" onClick={ onSignup }> 가입 </button> </td>
                          </tr>
                    </tbody>
              </table>
          </form>
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
