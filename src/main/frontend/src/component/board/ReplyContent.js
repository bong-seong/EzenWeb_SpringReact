import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import { ListItem , ListItemText , InputBase , Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

export default function ReplyContent( props ) {

    const [ reply , setReply ] = useState( props.reply );
    console.log( props.reply );

    const deleteReply = props.deleteReply;

    const deleteEventHandler = () => {
        deleteReply(reply);
    }

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
            </ListItemText>

            <ListItemSecondaryAction>
                <IconButton aria-label="Delete todo" onClick={ deleteEventHandler }>
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    </>);
}