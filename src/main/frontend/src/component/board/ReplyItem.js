import React , { useState , useEffect } from 'react';
import { useParams } from 'react-router-dom'; // HTTP 경로상의 매개변수 호출 해주는 함수
import axios from 'axios';

import { List , Paper , Container , Button } from '@mui/material';

import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { TreeView , TreeItem } from '@mui/lab';
import { ListItem , ListItemText , InputBase , Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

export default function ReplyItem( props ) {

    const [ replyItem , setReplyItem ] = useState( props.replyItem );

    return (<>
        <ListItem>
            <ListItemText>
                <InputBase
                    type="text"
                    id={ replyItem.rno }
                    value={ replyItem.rcontent }
                    multiline={ true }
                    fullWidth={ true }
                />
            </ListItemText>

            <ListItemSecondaryAction>
                <Button
                    style={{ height:'100%' }}
                    color="secondary"
                    variant="outlined"
                    id={ replyItem.rno }
                >
                    대댓글
                </Button>
                <IconButton aria-label="Delete todo">
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    </>);
}