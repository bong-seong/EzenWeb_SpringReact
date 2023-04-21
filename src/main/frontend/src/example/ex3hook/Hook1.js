import React , { useState , useEffect } from 'react';

// 1. 예제 훅[ useState() ]을 사용 안했을때
    // return : 렌더링 될 HTML 반환 해주는 곳 [ 화면에 표시할 코드 ]
    // 컴포넌트 당 1번씩만 실행 didMount
/*
export default function Hook1( props ) {

    let count = 0;

    const countHandler = () => {
        count++;
        alert( '현재 count : ' + count );
    }

    return (<>

        <div>
            <p> 총 { count } 번 클릭했습니다. </p>
        </div>
        <button onClick={ countHandler }>
            증가
        </button>

    </> );
}
*/

/*
// 2. 예제 훅 [ useState() 사용 ]
    // didMount ->
    // 상태변수 상태변경 : set~~() --> didupdate 재렌더링
    // 상태변경시 주소값 바뀜
export default function Hook1( props ) {

    console.log('Hook1 컴포넌트실행')

    const [ count , setCount ] = useState( 0 ) ;

    const countHandler = () => {
        alert( '현재 count : ' + count );
        setCount( count+1 );
    }

    return (<>

        <div>
            <p> 총 { count } 번 클릭했습니다. </p>
        </div>
        <button onClick={ countHandler }>
            증가
        </button>

    </>);
}
*/


export default function Hook1( props ) {

    console.log('Hook1 컴포넌트실행')

    const [ count , setCount ] = useState( 0 ) ;

    // 1. useEffect() 함수
        /*
            컴포넌트의 생명주기 [ Life Cycle ]
            mount : 첫생성 , update : 업데이트 , unmount : 제거
            1. useEffect( () => { } );
            2. mount , update 시 실행되는 함수
            3. 하나의 컴포넌트에서 여러번 사용 가능
            4. unmount 작동할 경우 return 사용
        */
    useEffect( () => {
        console.log( "useEffect1() 실행 총 클릭수 : " + count );
    })
    useEffect( () => {
        console.log( "useEffect2() 실행 총 클릭수 : " + count );
    })
    useEffect( () => {
        console.log( "useEffect3() 실행 총 클릭수 : " + count );
        return () => { console.log('컴포넌트가 제거 되었을때')} // unmount
    })


    const countHandler = () => {
        alert( '현재 count : ' + count );
        setCount( count+1 );
    }

    return (<>

        <div>
            <p> 총 { count } 번 클릭했습니다. </p>
        </div>
        <button onClick={ countHandler }>
            증가
        </button>

    </>);
}