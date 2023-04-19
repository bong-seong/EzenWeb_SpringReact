import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // 1.import 를 이용해 App 컴포넌트[함수] 를 불러온다.
import reportWebVitals from './reportWebVitals';
//----------------------------------------------------------------//
import Book from './example/ex1component/Book';
import Product from './example/ex1component/Product';
import ProductList from './example/ex1component/ProductList';
import Clock from './example/ex1component/Clock';
import Comment from './example/ex2css/Comment';
import CommentList from './example/ex2css/CommentList';
import AppTodo from './example/exbook/AppTodo';

const root = ReactDOM.createRoot(document.getElementById('root'));
/*
// 1. 예제1 개발자 정의 컴포넌트 렌더링
root.render(
    <React.StrictMode>f
        <Book />
    </React.StrictMode>
);
*/

/*
// 2. 예제2 개발자 정의 컴포넌트 렌더링
root.render(
    <React.StrictMode>
        <Product />
    </React.StrictMode>
);
*/

/*
// 3. 예제3
root.render(
    <React.StrictMode>
        <ProductList />
    </React.StrictMode>
);
*/

/*
// 4. 예제4 렌더링 반복
// 1초마다 해당 코드 실행 : setInterval( () => { } , 1000 );
setInterval( () => {
    root.render(
        <React.StrictMode>
            <Clock />
        </React.StrictMode>
    );
} , 1000 );
*/

/*
// 5. 예제5 css 적용
root.render(
    <React.StrictMode>
        <CommentList />
    </React.StrictMode>
);
*/

// 6. 교재 Todo 적용
root.render(
    <React.StrictMode>
        <AppTodo />
    </React.StrictMode>
);

/* 기본값
// 1. HTML에 존재하는 div 가져오기
// 2. ReactDOM.createRoot ( 해당 div ) : 해당 div를 리액트 root 로 사용하겠다 선언 [ 객체 생성 ]
// 3. root.render() : 해당 root 객체 ( 우리가 가져온 div ) 의 컴포넌트 렌더링
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
//2. <컴포넌트 > 를 이용해 컴포넌트를 사용한다.
// 4. App 이라는 컴포넌트를 render 함수에 포함 ( App 호출하는 방법 : 상단에 import )

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
*/