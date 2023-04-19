console.log("board.js 열림")

// 1. 카테고리 등록
function setCategory(){
    console.log("setCategory 실행")

    let cname = document.querySelector(".cname").value;

    $.ajax({
        url : "/board/category/write",
        method : "POST",
        data : JSON.stringify({ "cname" : cname }),
        contentType : "application/json",
        success : (r) => {
            console.log(r);
            if( r == true ){
                alert("카테고리 등록 성공")
                getCategory();
            }else{
                alert("등록 실패")
            }

        }
    })


}

// 2. 카테고리 출력
getCategory();
function getCategory(){
    console.log("getCategory 실행")

    $.ajax({
        url : "/board/category/list",
        method : "GET",
        success : (r) => {
            console.log(r);
            let html = `<button type="button" onclick="selectorCno(0)">전체보기</button>`;
            for ( let cno in r){
                console.log("키 : " + cno );
                console.log("키/필드에 저장된 값 : " + r[cno] );
                html += `<button type="button" onclick="selectorCno(${cno})">${ r[cno] }</button>`;
            }
            document.querySelector(".categorylistbox").innerHTML = html;
        }
    })
}
// 3. 카테고리 선택
let selectCno = 0; // 선택된 카테고리 번호 [ 기본값 = 0 ]
function selectorCno( cno ){
    console.log("selectCno 실행 : " + cno );
    selectCno = cno ; // 이벤트로 선택한 카테고리번호를 전역변수에 대입
    getBoard( cno );
}

// 4. 게시물 쓰기
function setBoard(){

    if( selectCno == 0 ){
        alert("작성할 게시물의 카테고리를 선택해주세요");
        return;
    }

    let btitle = document.querySelector('.btitle').value;
    let bcontent = document.querySelector('.bcontent').value;
    // @RequestBodt 이용한 json 자동으로 DTO 매핑하기 위해서 조건 [ 필드명 동일 ]

    $.ajax({
        url : "/board/write",
        method : "POST",
        data : JSON.stringify({ "btitle" : btitle, "bcontent" : bcontent, "cno" : selectCno }),
        contentType : "application/json",
        success : ( r ) => {
            console.log(r);
            if( r == 4 ){
                alert("글쓰기 성공")
                document.querySelector('.btitle').value = "";
                document.querySelector('.bcontent').value = "";
                getBoard( selectCno ); // 현재 선택된 카테고리 기준으로 게시물들을 재출력
            }else{
                alert("글쓰기 실패")
            }
        }
    })
}

// 5. 게시물들 출력 [ 선택된 카테고리의 게시물 출력 ]
getBoard( 0 );
function getBoard( cno ){

    selectCno = cno;

    $.ajax({
        url : "/board/list",
        method : "GET",
        data : { "cno" : selectCno },
        success : ( r ) => {
            console.log(r);

            let html = `<tr>
                            <th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th>
                        </tr>`;

            r.forEach( ( o ) => {
                html += `<tr>
                            <td>${ o.bno }</td>
                            <td onclick="selectBoard(${o.bno})">${ o.btitle }</td>
                            <td>${ o.memail }</td>
                            <td>${ o.bdate }</td>
                            <td>${ o.bview }</td>
                         </tr>`;

            })
            document.querySelector(".boardlistbox").innerHTML = html;
        }
    })
}

// 6. 내가 작성한 ( 로그인 되어있는 가정 ) 게시물
function myboards(){

    $.ajax({
        url : "/board/myboards",
        method : "GET",
        success : ( r ) => {
            console.log(r);
            let html = `<tr>
                            <th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th>
                        </tr>`;

            r.forEach( ( o ) => {
                html += `<tr>
                            <td>${ o.bno }</td>
                            <td>${ o.btitle }</td>
                            <td>${ o.memail }</td>
                            <td>${ o.bdate }</td>
                            <td>${ o.bview }</td>
                         </tr>`;

            })
            document.querySelector(".boardlistbox").innerHTML = html;
        }
    })

}

function selectBoard( bno ){
    console.log("board 실행 : " + bno );

    $.ajax({
        url : "/board/select",
        method : "GET",
        data : { "bno" : bno },
        success : ( r ) => {
            console.log(r);
            let html = ``;

            html = `<div> 글번호 : ${ r.bno } </div>
                    <div> 작성자 : ${ r.memail } </div>
                    <div> 작성일 : ${ r.bdate } </div>
                    <div> 제목 : ${ r.btitle } </div>
                    <div> 내용 : ${ r.bcontent } </div>
                    <button type="button" onclick="deleteBoard(${r.bno})"> 삭제 </button>
                    `

            document.querySelector(".boardbox").innerHTML = html;
        }
    })
}

function deleteBoard( bno ){
    console.log("deleteBoard 실행 : " + bno );

    $.ajax({
        url : "/board/delete",
        method : "delete",
        data : { "bno" : bno },
        success : ( r ) => {
            console.log(r);
            if( r == true ){
                alert("게시물 삭제 성공")
                document.querySelector(".boardbox").innerHTML = '';
                getBoard( selectCno );
            }else{
                alert("게시물 삭제 실패")
            }
        }
    })
}


/*
    해당 변수의 자료형 확인 Prototype
    Array : forEach() 가능
        { object , object , object }
    object : forEach() 불가능 ---> for( let key in object){ }
        {
            필드명 : 값 ,
            필드명 : 값 ,
            필드명 : 값
        }
        object[필드명] : 해당 필드의 값 호출

*/