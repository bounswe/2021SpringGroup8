import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {alertTitleClasses, Avatar} from "@mui/material";
import AuthService from "../../services/auth.service";
import userCommunityService from "../../services/user-community.service";


function CommunityHeader(props) {

    const {title, id, subscribed,  subscribers} = props;
    let subscribedlocal = false
    const initialState = {
        variant: subscribed ? "outlined" : "contained",
        text: subscribed ? "Unsubscribe" : "Subscribe"
    }
    useEffect(() => {
        setSubs(subscribed)
        setVariant((subs ? "outlined" : "contained"))
        setText((subs ? "Unsubscribe" : "Subscribe"))
    })

    const [variant, setVariant] = useState(initialState.variant);
    const [subs, setSubs] = useState(subscribed);
    const [text, setText] = useState(initialState.text);
    const toggle = React.useCallback(
        () => setSubs(!subs),
        [subs, setSubs],
    );

    function subscribe() {
        if(subs) {
            userCommunityService.unsubscribeCommunity(id).then(response => {
                console.log(response)
                if (response.data['@success'] === "True") {
                    setSubs(false)
                    setVariant((subs ? "outlined" : "contained"))
                    setText((subs ? "Unsubscribe" : "Subscribe"))
                    alert('unsubscribed')
                } else {
                    console.log(response.data['@error'])
                    alert(response.data['@error'])
                }
            }).catch(error => {
                console.log(error);
            });
        }else {
            userCommunityService.subscribeCommunity(id).then(response => {
                console.log(response.data)
                if (response.data['@success'] === "True") {
                    setSubs(true)
                    setVariant((subs ? "outlined" : "contained"))
                    setText((subs ? "Unsubscribe" : "Subscribe"))
                    alert('subscribed')
                } else {
                    console.log(response.data['@error'])
                    alert(response.data['@error'])
                }
            }).catch(error => {
                console.log(error);
            });
        }
    }

    return (
        <Toolbar sx={{height: "100px", borderBottom: 1, borderColor: 'divider'}}>
            <Avatar alt={title} src="https://picsum.photos/200/200" sx={{width: 100, height: 100}}/>
            <div style={{marginLeft: 10}}>
                <Button id="subscribe" size="Large" variant={variant} onClick={subscribe}>{text}</Button>
            </div>
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
};

export default CommunityHeader;
