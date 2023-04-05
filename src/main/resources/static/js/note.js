console.log("js 열림")

// 1. 등록
function onwrite(){

    // Postmapping
    $.ajax({
        url : "/note/write",
        method : "POST",
        contentType : "application/json",
        data : JSON.stringify(
            {
                "ncontent" : document.querySelector(".ncontent").value,
            }
        ),
        success : ( r ) => {
            console.log(r);
            if( r == true ){
                alert("글쓰기 성공")
                onget();
            }else{
                alert("글쓰기 실패")
            }
        }
    });
}// end


// 2. 호출
onget();
function onget(){

    let html = `<tr>
                    <th> 번호 </th> <th> 내용 </th> <th> 비고 </th>
                </tr>`;

    $.ajax({
        url : "/note/get",
        method : "GET",
        success : ( r ) => {
            console.log(r);

            r.forEach( (o) => {

                html += `<tr>
                            <td>${o.nno}</td>
                            <td>${o.ncontent}</td>
                            <td>
                                <button type="button" onclick="onupdate(${o.nno})"> 수정 </button>
                                <button type="button" onclick="ondelete(${o.nno})"> 삭제 </button>
                            </td>
                        </tr>`;
            });
            document.querySelector(".notetable").innerHTML = html;
        }
    });
}

// 3. 삭제
function ondelete(nno){
    $.ajax({
        url : "/note/delete",
        method : "delete",
        data : { "nno" : nno },
        success : ( r ) => {
            console.log(r);
            if( r == true ){
                alert("삭제 성공")
                onget();
            }else{
                alert("삭제 실패")
            }
        }
    });
}

// 4. 수정
function onupdate( nno ){

    let ncontent = prompt("수정할 내용을 입력하세요");

    $.ajax({
        url : "/note/update",
        method : "PUT",
        contentType : "application/json",
        data : JSON.stringify( { "nno" : nno , "ncontent" : ncontent } ),
        success : ( r ) => {
            console.log(r);
            if( r == true ){
                alert("수정 성공")
                onget();
            }else{
                alert("수정 실패")
            }
        }
    })
}


