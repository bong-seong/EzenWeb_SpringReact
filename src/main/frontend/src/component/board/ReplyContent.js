import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import { ListItem , ListItemText , InputBase , Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';
import AddReply from './AddReply';

export default function ReplyContent( props ) {

    const [ reply , setReply ] = useState( props.reply );
    const [ replies , setreplies ] = useState( props.replies );
    console.log( props.reply );
    console.log( props.replies );

    const deleteReply = props.deleteReply;

    const deleteEventHandler = () => {
        deleteReply(reply);
    }

    const rereply = () => {

    }

    let rereplySw = true;
    const rereplyEventHandler = () => {
        if( rereplySw ){
            document.querySelector('.rereply').style.display = 'block';
            rereplySw = false;
        }else{
            document.querySelector('.rereply').style.display = 'none';
            rereplySw = true;
        }
    }


    let rereplyContent = () => {
        replies.map( (o) => {
            if( reply.rno == o.rindex ){
                return (<>
                    <ListItem>
                        <ListItemText>
                            <InputBase
                                type="text"
                                id={ o.rno }
                                value={ o.rcontent }
                                multiline={ true }
                                fullWidth={ true }
                            />
                        </ListItemText>

                        <ListItemSecondaryAction>
                            <IconButton aria-label="Delete todo" onClick={ deleteEventHandler }>
                                <DeleteOutlined />
                            </IconButton>
                        </ListItemSecondaryAction>
                    </ListItem>
                </>);
            }
        })
    }

    console.log(rereplyContent)

    return (<>
        <ListItem>
            <ListItemText>
                <InputBase
                    type="text"
                    id={ reply.rno }
                    value={ reply.rcontent }
                    multiline={ true }
                    fullWidth={ true }
                />
                <div className="rereply" style={{display : "none"}}>
                    { rereplyContent() }
                    <AddReply />
                </div>
            </ListItemText>

            <ListItemSecondaryAction>
                <IconButton onClick={ rereplyEventHandler }>
                    +
                </IconButton>
                <IconButton aria-label="Delete todo" onClick={ deleteEventHandler }>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    </>);
}