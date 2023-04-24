import React from 'react';
import { BrowserRouter , Routes , Route } from 'react-router-dom';
import Login from './member/Login';
import Header from './Header';
import Main from './Main';
import Footer from './Footer';
import Signup from './member/Signup';
import Find from './member/Find';
import List from './board/List';
import Write from './board/Write';
import DashBoard from './admin/DashBoard';



/*
    react-router-dom 다양한 라우터 컴포넌트 제공
    1. <BrowserRouter>  : 가상 URL 관리 [ 브라우저 URL 동기화 ]
    2. <Routes>         : 가장 적합한 <Route> 컴포넌트를 검토하고 찾는다.
            요청된 path 에 적합한 <Route> 찾아서 <Routes> 범위내 렌더링
    3. <Route>          : 실제 URL 경로를 지정해주는 컴포넌트
        <Route path="login" element={ <Login /> } />
        http://localhost:3000/login     get 요청시 Login 컴포넌트 반환
*/


export default function Index( props ){
    return (<>
         <BrowserRouter>

            <Header />

            <Routes>
                <Route path="/" element={ <Main /> } />
                <Route path="/member/login" element={ <Login /> } />
                <Route path="/member/signup" element={ <Signup /> } />
                <Route path="/member/find" element={ <Find /> } />
                <Route path="/board/list" element={ <List /> } />
                <Route path="/admin/dashboard" element={ <DashBoard /> } />
                <Route path="/board/write" element={ <Write /> } />
            </Routes>

            <Footer />

         </BrowserRouter>
    </>);
}

