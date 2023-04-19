/*
컴포넌트 원형

import React from 'react';
export default function Product( props ){ return ( <> </> ); }
*/
// <Product name='코카콜라' price='1000' />
// props : properties 줄임말 [ 매개변수를 JSON 형태로 받는다 ]
import React from 'react';
export default function Product( props ){

    console.log( props );

    return ( <>
        <div>
            <h5> 제품명 : { props.name } </h5>
            <h6> 가격 : { props.price } </h6>
        </div>
    </> );
}
