import React , { useState , useEffect } from 'react';
import axios from 'axios';
import CategoryList from './CategoryList'
/* ------------------ table mui ---------------- */
import { Table , TableBody , TableCell , TableContainer , TableHead , TableRow , Paper , Container , Button } from '@mui/material';
/*
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
*/
/* ---------------------------------- */
import Pagination from '@mui/material/Pagination';

export default function List( props ){

    // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수 [ 상태관리 변수 ]
    let [ rows , setRows ] = useState( [] )
    let [ pageInfo , setPageInfo] = useState( { 'cno' : 0 , 'page' : 1 , 'key' : '' , 'keyword' : '' } );
    let [ totalPage , setTotalPage ] = useState( 1 );
    let [ totalCount , setTotalCount ] = useState( 1 );


    // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성되었을때 ]
    // useEffect( ()=> {} , [] ) vs useEffect( ()=> {} )
    useEffect( () => {
        axios.get('/board', { params: pageInfo } )
            .then( r => {
                console.log(r);
                setRows( r.data.boardDtoList );     // 응답받은 게시물 대입
                setTotalPage( r.data.totalPage );   // 응답받은 페이지수 대입
                setTotalCount( r.data.totalCount ); // 응답받은 총 게시물수 대입
            })
            .catch( err => { console.log(err); })
    }, [ pageInfo ] ) // pageInfo ( cno , page ) 변경될때마다 해당 useEffect 실행

    // useEffect( ()=> {} )             : 생성 , 업데이트
    // useEffect( ()=> {} , [])         : 생성될때 1번
    // useEffect( ()=> {} , [변수] )     : 생성 , 해당 변수가 업데이트 될때마다 새 렌더링


    // 3. 카테고리 변경
    const categoryChange = ( cno ) => {
        pageInfo.cno = cno;
        setPageInfo( {...pageInfo} );
    }
    // [ ...배열명 ] , { ...객체명 } : 기존 배열/객체의 새로운 메모리 할당

    // 4. 페이징 번호 선택

    const selectPage = ( event, value ) => {
        // console.log( e );
        // console.log( e.target );             // button
        // console.log( e.target.value );       // button 이라서 value 속성 없음 x
        // console.log( e.target.innerHTML );   // 해당 button 에서 안에 작성된 html 호출
        // console.log( e.target.outerText );      // 해당 tag ( button )  밖에 있는 text 출력
        console.log( value );
        pageInfo.page = value;
        setPageInfo( {...pageInfo} );
    }

    // 5. 검색 했을때
        // const 상수 vs let 변수
        // 왜?? const
    const onSearch = () => {
        console.log( document.querySelector('.key').value );
        console.log( document.querySelector('.keyword').value );
        pageInfo.key = document.querySelector('.key').value
        pageInfo.keyword = document.querySelector('.keyword').value
        pageInfo.page = 1 // 검색했을때 첫페이지 이동
        setPageInfo( {...pageInfo} );
    }

    return (<>
        <Container>
            <div> 현재페이지 : { pageInfo.page }  / 게시물수 : { totalCount } </div>
            <div style={{ display:'flex' , justifyContent:'space-between' , alignItems:'center' }}>
                <CategoryList categoryChange = {categoryChange} />
                <a href="/board/write"><Button variant="outlined"> 글쓰기 </Button></a>
            </div>
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell align="center" style={{width:'10%'}}>번호</TableCell>
                    <TableCell align="center" style={{width:'50%'}}>제목</TableCell>
                    <TableCell align="center" style={{width:'10%'}}>작성자</TableCell>
                    <TableCell align="center" style={{width:'20%'}}>작성일</TableCell>
                    <TableCell align="center" style={{width:'10%'}}>조회수</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows.map((row) => (
                    <TableRow
                      key={row.bno}
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <TableCell align="center">{row.bno}</TableCell>
                      <TableCell align="center"><a href={ '/board/view/'+row.bno }>{row.btitle}</a></TableCell>
                      <TableCell align="center">{row.memail}</TableCell>
                      <TableCell align="center">{row.bdate}</TableCell>
                      <TableCell align="center">{row.bview}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>

            <div style={{ display:'flex' , justifyContent : 'center' , margin: '40px 0px'}}>
                { /* count = 전체 페이지수 */ }
                <Pagination count={ totalPage } color="secondary" onChange={ selectPage } />
            </div>

            <div>
                <select className="key">
                    <option value="btitle"> 제목 </option>
                    <option value="bcontent"> 내용 </option>
                </select>
                <input type="text" className="keyword" />
                <button type="button" onClick={ onSearch } > 검색 </button>
            </div>

        </Container>
    </>);
}



