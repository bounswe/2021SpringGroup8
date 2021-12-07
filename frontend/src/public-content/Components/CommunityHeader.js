import * as React from 'react';
import PropTypes from 'prop-types';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {Avatar} from "@mui/material";

function CommunityHeader(props) {

    const {name, title} = props;

    return (
        <Toolbar sx={{height: "100px", borderBottom: 1, borderColor: 'divider'}}>
            <Avatar alt={title} src="https://picsum.photos/200/200" sx={{width: 100, height: 100}}/>
            <Button size="Large">{name}</Button>
            <Typography
                component="h2"
                variant="h5"
                color="inherit"
                align="center"
                sx={{flex: 1}}
            >
                {title}
            </Typography>
        </Toolbar>
    );
}

CommunityHeader.propTypes = {
    title: PropTypes.string.isRequired,
    name: PropTypes.string.isRequired,
};

export default CommunityHeader;
