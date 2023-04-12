console.log('member.js 실행');

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
            if( r == true ) alert( '가입완료' );
        }
    })
}

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
                if( r == true ) alert( '로그인완료' );
            }
        })
}