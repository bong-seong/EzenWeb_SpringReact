import React , { useState , useEffect } from 'react';
import axios from 'axios';

import { DataGrid } from '@mui/x-data-grid'; // npm install 패키지명

// Data 테이블의 필드 설정
const columns = [
  { field: 'id', headerName: '제품번호', width: 150 },
  { field: 'pname', headerName: '제품명', width: 150 },
  { field: 'pprice', headerName: '가격', type: 'number' , width: 70 },
  { field: 'pcategory', headerName: '카테고리', width: 70 },
  { field: 'pcomment', headerName: '제품 설명', width: 50 },
  { field: 'pmanufacturer', headerName: '제조사', width: 100 },
  { field: 'pstate', headerName: '상태', type: 'number' , width: 30 },
  { field: 'pstock', headerName: '재고수량', width: 70 },
  { field: 'cdate', headerName: '최초등록일', width: 100 },
  { field: 'udate', headerName: '최근수정일', width: 100 }
];


export default function ProductTable( props ) {

    // 1. 상태변수
    const [ rows , setRows ] = useState([]);

    // 4. 데이터테이블에서 선택된 제품 리스트
    const [ rowSelectionModel, setRowSelectionModel ] = React.useState([]);
    console.log( rowSelectionModel );

    // 2. 제품호출 axios
    const getProduct = ()  => {
        axios.get("/product").then( r => {
            setRows( r.data )
        })
    }

    // 3. 컴포넌트 생명주기에 따른 함수 호출
    useEffect( () => {
        getProduct();
    } , [] )

    console.log( rows );

    const onDeleteHandler = () => {
        console.log( "onDeleteHandler 클릭" );

        let msg = window.confirm("정말 삭제하시겠습니까? 복구가 불가능합니다.");

        if( msg == true ){ // 확인 버튼을 클릭했을때
            // 선택된 제품리스트를 하나씩 서버에게 전달.
            rowSelectionModel.forEach( o => {
                axios.delete("/product" , { params : { id : o } }).then( r => {
                    console.log( r.data );
                    if( r.data ) getProduct();
                })
            })
        }
    }

    return (<>
        <button
            type="button"
            onClick={ onDeleteHandler }
            disabled={ rowSelectionModel.length == 0 ? true : false }
        > 선택삭제 </button>
        <div style={{ height: 400, width: '100%' }}>
            <DataGrid
                rows={ rows }
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 5 },
                    },
                }}
                pageSizeOptions={[ 5, 10 , 15 , 20 ]}
                checkboxSelection
                onRowSelectionModelChange={ (newRowSelectionModel) => {
                    setRowSelectionModel(newRowSelectionModel);
                }}
            />
        </div>
    </>);
}