import * as React from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import {Grid, Paper} from "@mui/material";

function CommunityPosts(props) {

    const {posts} = props;
    return (
        <Grid item xs={12} md={8}>
            <div style={{backgroundColor: "gray", margin: 10}}>
                <Typography variant="h4" align="center" gutterBottom style={{fontWeight: 600}}>
                    Posts
                </Typography>
            </div>
        </Grid>
    );
}

CommunityPosts.propTypes = {
    posts: PropTypes.array.isRequired,
};

export default CommunityPosts;
