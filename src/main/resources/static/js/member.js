console.log('member.js 실행');
let memberInfo = '';
function onSignup(){

    let info ={
        memail : document.querySelector('.memail').value,
        mname : document.querySelector('.mname').value,
        mpassword : document.querySelector('.mpassword').value,
        mphone : document.querySelector('.mphone').value
    }

    $.ajax({
        url: "/member/info",
        method: "POST",
        data: JSON.stringify(info),
        contentType: "application/json",
        success: ( r ) => {
            console.log( r );
            if( r == true ) alert( '가입완료' )
            else alert( '중복되는 아이디 입니다.');
            location.href="/";
        }
    })
}


// 2. 로그인
function onLogin(){

    let loginForm = document.querySelectorAll('.loginForm')[0];
    let loginFormData = new FormData(loginForm);

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
}

/*
// 시큐리티 사용하므로 폼전송으로 로그인 대체
function onLogin(){
    let info ={
            memail : document.querySelector('.memail').value,
            mpassword : document.querySelector('.mpassword').value
        }

        $.ajax({
            url: "/member/login",
            method: "POST",
            data: JSON.stringify(info),
            contentType: "application/json",
            success: ( r ) => {
                console.log( r );
                if( r == true ) {
                    alert( '로그인완료' );
                    location.href = '/' ;
                }
            }
        })
}
*/

getMember();
function getMember(){
    $.ajax({
        url: "/member/info",
        method: "GET",
        async: false,
        success: ( r ) => {
            console.log( r ); console.log( r.mname ); console.log( r.mno );
            memberInfo = r;
            if( r.mname != null){
                document.querySelector('.infobox').innerHTML = r.mname + '님' ;
                document.querySelector('.infobox').innerHTML += `<a href="/member/logout"><button type="button"> 로그아웃 </button></a>
                                                                             `



            }

        }
    })
}

/*
function getLogout(){
    $.ajax({
        url: "/member/logout",
        method: "GET",
        success: ( r ) => {
            console.log( r );
            if( r == true ) {
                alert( '로그아웃완료' );
                location.href = '/' ;
            }
        }
    })
}
*/

function findid(){
    console.log("findid 실행");

    let info ={
        mname : document.querySelector('.mname').value,
        mphone : document.querySelector('.mphone').value
    }

    $.ajax({
        url : "/member/findid",
        method: "POST",
        data: JSON.stringify(info),
        async: false,
        contentType: "application/json",
        success: ( r ) => {
            console.log( r );
            document.querySelector('.findid').innerHTML = '회원님의 아이디는 ' + r + ' 입니다.';
        }
    })
}

function findpw(){
    console.log("findpw 실행");

    let info ={
        memail : document.querySelector('.memail').value,
        mphone : document.querySelector('.mphone').value
    }

    $.ajax({
        url : "/member/findpw",
        method: "POST",
        data: JSON.stringify(info),
        contentType: "application/json",
        success: ( r ) => {
            console.log( r );
            if( r == null){
                alert("입력하신 정보가 올바르지 않습니다.");
            }else{
                document.querySelector('.findpw').innerHTML = '회원님의 임시비밀번호는' + r + '입니다.';
            }
        }
    })
}


function userdelete(){

    let mpassword = document.querySelector('.mpassword').value;

    $.ajax({
        url : "/member/info",
        method: "DELETE",
        data : { mno : memberInfo.mno , mpassword : mpassword },
        success: ( r ) => {
            if( r == true ) {
                alert("회원탈퇴 완료")
                getMember();
                location.href = '/member/logout'

            }else {
                alert("회원탈퇴 실패")
            }
        }
    })
}

function onupdate(){
    console.log("onupdate 실행");
    console.log(memberInfo.mno);

    let info = {
        mno : memberInfo.mno,
        mname : document.querySelector('.umname').value,
        mphone : document.querySelector('.umphone').value
    }

    $.ajax({
        url : "/member/info",
        method: "PUT",
        data: JSON.stringify(info),
        contentType: "application/json",
        success: ( r ) => {
            console.log( r );
            if( r == true ) {
                alert("수정 성공")
                location.href = '/'
                getMember();
            }else{
                alert("수정 실패")
            }
        }
    })


}
