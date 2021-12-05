import * as React from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import {Grid} from "@mui/material";

function CommunityAbout(props) {

    const {description, moderators, tags} = props;

    return (
        <Grid item xs={12} md={4}>
            <div style={{backgroundColor: "gray", borderRadius: 10, margin: 10}}>
                <Typography variant="h6" style={{
                    fontWeight: 600,
                    borderTopLeftRadius: 10,
                    borderTopRightRadius: 10,
                    backgroundColor: "blue",
                    padding: 5,
                    marginTop: 5,
                    color: "white"
                }}>
                    About
                </Typography>
                <Typography paragraph>{description} </Typography>
            </div>

            <div style={{backgroundColor: "gray", borderRadius: 10, margin: 10, marginTop: 30}}>
                <Typography variant="h6" style={{
                    fontWeight: 600,
                    borderTopLeftRadius: 10,
                    borderTopRightRadius: 10,
                    backgroundColor: "blue",
                    padding: 5,
                    marginTop: 5,
                    color: "white"
                }}>
                    Tags
                </Typography>
                <Typography paragraph>{tags} </Typography>
            </div>


            <div style={{backgroundColor: "gray", borderRadius: 10, margin: 10, marginTop: 30}}>
                <Typography variant="h6" style={{
                    fontWeight: 600,
                    borderTopLeftRadius: 10,
                    borderTopRightRadius: 10,
                    backgroundColor: "blue",
                    padding: 5,
                    marginTop: 5,
                    color: "white"
                }}>
                    Moderators
                </Typography>
                <Typography paragraph>{moderators} </Typography>
            </div>
        </Grid>
    );
}

CommunityAbout.propTypes = {
    description: PropTypes.string.isRequired,
};

export default CommunityAbout;
