import * as React from 'react';
import Box from '@mui/material/Box';
import SpeedDial from '@mui/material/SpeedDial';
import SpeedDialIcon from '@mui/material/SpeedDialIcon';
import SpeedDialAction from '@mui/material/SpeedDialAction';
import PrintIcon from '@mui/icons-material/Print';
import ShareIcon from '@mui/icons-material/Share';

const user_actions = [
    {icon: <ShareIcon/>, name: 'Share Post'},
];

const owner_actions = [
    {icon: <PrintIcon/>, name: 'Create Data Type'},
    {icon: <ShareIcon/>, name: 'Share Post'},
];

export default function BasicSpeedDial(props) {
    const {owner} = props;
    const actions = owner ? owner_actions : user_actions;
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
                ariaLabel="SpeedDial basic example"
                sx={{position: 'absolute', bottom: 16, right: 16}}
                icon={<SpeedDialIcon/>}
            >
                {actions.map((action) => (
                        <SpeedDialAction
                            key={action.name}
                            icon={action.icon}
                            tooltipTitle={action.name}
                        />
                    )
                )}
            </SpeedDial>
        </Box>
    );
}
