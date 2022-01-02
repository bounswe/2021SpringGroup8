import * as React from 'react';
import Box from '@mui/material/Box';
import SpeedDial from '@mui/material/SpeedDial';
import SpeedDialIcon from '@mui/material/SpeedDialIcon';
import SpeedDialAction from '@mui/material/SpeedDialAction';
import PrintIcon from '@mui/icons-material/Print';
import ShareIcon from '@mui/icons-material/Share';
import {useHistory} from "react-router-dom";

const user_actions = [
    {icon: <ShareIcon/>, name: 'Share Post', operation: "post"},
];

const owner_actions = [
    {icon: <PrintIcon/>, name: 'Create Data Type', operation: "type"},
    {icon: <ShareIcon/>, name: 'Share Post', operation: "post"},
];

export default function BasicSpeedDial(props) {
    const {owner, communityId} = props;
    const history = useHistory()
    const actions = owner ? owner_actions : user_actions;

    function handleClick(event, operation) {
        event.preventDefault();

        if (operation === 'type') {
            console.log(operation)
            history.push('/createDataType/' + communityId)
            return
        } else if (operation === 'post') {
            history.push('/createPost/' + communityId)
            return
        }

    }

    return (
        <Box sx={{
            height: 0,
            transform: 'translateZ(0px)',
            flexGrow: 1,
            position: 'fixed',
            right: 50,
            bottom: 50,
            zIndex: 999
        }}>
            <SpeedDial
                ariaLabel="SpeedDial"
                sx={{position: 'absolute', bottom: 16, right: 16}}
                onClick={(event) => handleClick(event, null)}
                icon={<SpeedDialIcon/>}
            >
                {actions.map((action) => (
                        <SpeedDialAction
                            data-testid={action.operation}
                            key={action.name}
                            icon={action.icon}
                            tooltipTitle={action.name}
                            onClick={(e) => {
                                handleClick(e, action.operation)
                            }}
                        />
                    )
                )}
            </SpeedDial>
        </Box>
    );
}
