import React , { useState , useEffect , useRef } from 'react';
import axios from 'axios';

export default function ProductWrite( props ) {

    // 1.
    const writeForm = useRef(null);

    // 2.
    const onWriteHandler = () => {
        console.log("onWriteHandler 클릭");
        let writeFormData = new FormData( writeForm.current );
        console.log( writeFormData );

        axios.post("/product" , writeFormData ).then( r => {
            if( r.data ){
                alert( "등록성공" )
                props.tabChange('3');
            }else{
                alert( "등록실패" )
            }
        })
    }

    return (<>
        <form ref={ writeForm }>
            제품명 : <input type="text" name="pname" />
            <br/>
            제품가격 : <input type="text" name="pprice" />
            <br/>
            제품카테고리 : <input type="text" name="pcategory" />
            <br/>
            제품설명 : <input type="text" name="pcomment" />
            <br/>
            제품제조사 : <input type="text" name="pmanufacturer" />
            <br/>
            제품초기상태 : <input type="text" name="pstate" />
            <br/>
            제품재고 : <input type="text" name="pstock" />
            <br/>
            제품이미지 : <input
                            type="file" multiple
                            accept="image/jpeg,image/png,image/gif"
                            name="pimgs"
                       />
            <br/>
            <button type="button" onClick={ onWriteHandler }> 제품등록 </button>
        </form>
    </>);
}