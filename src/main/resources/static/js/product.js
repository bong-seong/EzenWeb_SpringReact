console.log('js열림');

// 1. 등록
function prodAdd(){

    let info = {
        pname : document.querySelector('.pname').value,
        pcontent : document.querySelector('.pcontent').value
    }

    $.ajax({
        url : "/item/write",
        method : "POST",
        contentType : "application/json",
        data : JSON.stringify(info),
        success : ( r ) => {
            if( r == true ){
                alert("제품 등록 성공");
            }else{
                alert("제품 등록 실패");
            }
            get();
        }
    })
}

get();
function get(){

    $.ajax({
        url : "/item/get",
        method : "GET",
        success : ( r ) => {
            console.log(r);

            let html = `<tr>
                            <th> 번호 </th> <th> 제품명 </th> <th> 제품설명 </th> <th> 비고 </th>
                        </tr>`;

            r.forEach( (o,i) => {
                html += `<tr>
                            <td>${o.pno}</td>
                            <td>${o.pname}</td>
                            <td>${o.pcontent}</td>
                            <td>
                                <button class="btn btn-delete" onclick="prodDel(${o.pno})">삭제</button>
                                <button class="btn btn-update" onclick="prodUp(${o.pno})">수정</button>
                            </td>
                        </tr>`;
            })
            document.querySelector('.productTable').innerHTML = html;
        }
    })
}

// 3. 삭제
function prodDel(pno){

    $.ajax({
        url : "/item/delete",
        method : "DELETE",
        data : {pno : pno},
        success : ( r ) => {
            if( r == true ){
                alert("제품 삭제 성공");
            }else{
                alert("제품 삭제 실패");
            }
            get();
        }
    })
}

function prodUp(pno){
    let info = {
        pno : pno,
        pname : prompt("수정할 제품명을 입력하세요"),
        pcontent : prompt("수정할 내용을 입력하세요")
    }

    $.ajax({
        url : "/item/update",
        method : "PUT",
        contentType : "application/json",
        data : JSON.stringify(info),
        success : ( r ) => {
            if( r == true ){
                alert("제품 수정 성공");
            }else{
                alert("제품 수정 실패");
            }
            get();
        }
    })
}