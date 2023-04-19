// CommentList.js
import React from 'react';
import Comment from './Comment.js';

export default function CommentList( props ){

    // 서버[ Ajax ] 로 부터 받은 데이터 [ JSON ] 예시
    let r = [
        { name : "유재석" , comment : "안녕하세요" },
        { name : "강호동" , comment : "네 안녕하세요" },
        { name : "신동엽" , comment : "안녕히계세요" }
    ];

    console.log( r );

    // return 안에서 js 문 사용시 { } => jsx 문법
    // jsx 주석 =? {/* 주석 */}
    // map [ return 가능 ] vs forEach [ return 불가능 ]

    return ( <>
        <div>
            {
            /* jsx 시작 */

                r.map ( (c) => {
                    return ( <Comment name={ c.name } comment={ c.comment } /> )
                })

            /* jsx 끝 */
            }
        </div>
    </> );
}
