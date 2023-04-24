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
export default function List( props ){

    // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수 [ 상태관리 변수 ]
    let [ rows , setRows ] = useState( [] )
    let [ cno , setCno] = useState( 0 );


    // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성되었을때 ]
    // useEffect( ()=> {} , [] ) vs useEffect( ()=> {} )
    useEffect( () => {
        axios.get('http://localhost:8080/board/list', { params: { cno : cno } } )
            .then( r => {
                console.log(r);
                setRows( r.data );
            })
            .catch( err => { console.log(err); })
    }, [ cno ] ) // cno 변경될때마다 해당 useEffect 실행

    // useEffect( ()=> {} )             : 생성 , 업데이트
    // useEffect( ()=> {} , [])         : 생성될때 1번
    // useEffect( ()=> {} , [변수] )     : 생성 , 해당 변수가 업데이트 될때마다 새 렌더링


    // 3. 카테고리 변경
    const categoryChange = ( cno ) => {
        setCno( cno );
    }


    return (<>
        <Container>
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
                      <TableCell align="center">{row.btitle}</TableCell>
                      <TableCell align="center">{row.memail}</TableCell>
                      <TableCell align="center">{row.bdate}</TableCell>
                      <TableCell align="center">{row.bview}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
        </Container>
    </>);
}