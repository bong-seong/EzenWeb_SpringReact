import React , { useState , useEffect }from 'react';
import styles from '../../css/member/find.css';
import { Container , Box , TextField , Button } from '@mui/material';
import axios from 'axios';


export default function Find( props) {

    let [ returnID , setReturnID ] = useState('');
    let [ returnPW , setReturnPW ] = useState('');

    const findID = () => {
        console.log("findid 실행");

        let info ={
            mname : document.querySelector('#mname').value,
            mphone : document.querySelector('#mphone').value
        }

        console.log( info );

        axios.post("/member/findid" , info)
            .then( r => {
                console.log(r);
                if( r.data != "") {
                    setReturnID( `회원님의 아이디는 ${r.data} 입니다.` );
                }else{
                    setReturnID( "일치하는 회원정보가 없습니다." );
                }
            })
    }

    const findPW = () =>{
        console.log("findpw 실행");

        let info ={
            memail : document.querySelector('#memail').value,
            mphone : document.querySelector('#mphone').value
        }

        axios.post("/member/findpw", info)
            .then( r => {
                console.log( r );
                if( r.data == '') {
                    setReturnPW('일치하는 회원 정보가 없습니다.')
                }else{
                    setReturnPW('신규 생성된 임시비밀번호는 ' + r.data + ' 입니다.')
                }
            })
    }

    let selectYN = false;

    const selectTab = () => {
        if( selectYN ){
            document.querySelector('.findPW').style.display = 'none';
            document.querySelector('.findID').style.display = 'block';
            document.querySelector('.PW-BOX').classList.add('tab-menu-none-selected');
            document.querySelector('.ID-BOX').classList.remove('tab-menu-none-selected');
            selectYN = false;
        }else{
            document.querySelector('.findPW').style.display = 'block';
            document.querySelector('.findID').style.display = 'none';
            document.querySelector('.ID-BOX').classList.add('tab-menu-none-selected');
            document.querySelector('.PW-BOX').classList.remove('tab-menu-none-selected');
            selectYN = true;
        }
    }

    return (<>
        <Container>
            <div className="find-mainBox">
            <h3 className="pageTitle"> 회원정보찾기 [ 아이디 / 비밀번호 ] </h3>
                <div className="tab-box">
                    <div className="tab-menu ID-BOX" onClick={ selectTab }>
                        아이디찾기
                    </div>
                    <div className="tab-menu PW-BOX tab-menu-none-selected" onClick={ selectTab }>
                        비밀번호찾기
                    </div>
                </div>
                <div className="inputBox">
                    <div className="findID">
                        <TextField fullWidth className="mname" id="mname" label="이름" variant="standard" />
                        <TextField fullWidth className="mphone" id="mphone" label="전화번호" variant="standard" />
                        <Button fullWidth variant="outlined" onClick={ findID } className="findBtn"> 아이디찾기 </Button>
                        <div className="returnBox"> { returnID } </div>
                    </div>

                    <div className="findPW">
                        <TextField fullWidth className="memail" id="memail" label="아이디/이메일" variant="standard" />
                        <TextField fullWidth className="mphone" id="mphone" label="전화번호" variant="standard" />
                        <Button fullWidth variant="outlined" onClick={ findPW } className="findBtn"> 비밀번호찾기 </Button>
                        <div className="returnBox"> { returnPW } </div>
                    </div>
                </div>
            </div>
        </Container>
    </>);
}