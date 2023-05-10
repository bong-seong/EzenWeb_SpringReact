import React , { useState , useEffect } from 'react';
import axios from 'axios';

import ProductTable from './ProductTable';
import ProductWrite from './ProductWrite';

import { Container } from '@mui/material';

// tabs
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

export default function DashBoard( props ) {

    // 현재 선택된 탭 번호
    const [value, setValue] = React.useState('1');

    // 탭 체인지시 value 값 변경
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    // 1. 카테고리 등록 버튼 눌렀을때 이벤트
    const setCategory = () => {

        let cname = document.querySelector(".cname");

        axios.post("/board/category/write", { "cname" : cname.value })
            .then( (r) => {
                console.log( r );
                if( r.data == true ){
                    alert("카테고리 등록 성공")
                    cname.value = '';
                }else{
                    alert("등록 실패")
                }
         })
    }

    const tabChange = ( value ) => {
        setValue( value );
    }


    return(<>
        <Container>

            <Box sx={{ width: '100%', typography: 'body1' }}>
                <TabContext value={value}>
                    <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                        <TabList onChange={handleChange} aria-label="lab API tabs example">
                            <Tab label="게시판카테고리등록" value="1" />
                            <Tab label="제품등록" value="2" />
                            <Tab label="제품관리" value="3" />
                            <Tab label="제품통계" value="4" />
                        </TabList>
                    </Box>

                    <TabPanel value="1">
                        <h6> 게시판 카테고리 추가 </h6>
                        <input type="text" className="cname" />
                        <button onClick={ setCategory } type="button"> 카테고리등록 </button>
                    </TabPanel>

                    <TabPanel value="2">
                        <ProductWrite
                            tabChange={ tabChange }
                        />
                    </TabPanel>

                    <TabPanel value="3">
                        <ProductTable />
                    </TabPanel>

                    <TabPanel value="4">
                        제품통계 구역
                    </TabPanel>
                </TabContext>
            </Box>
        </Container>
    </>);
}