import * as React from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import {Grid, Paper} from "@mui/material";
import Post from "./Post";
import FlipMove from "react-flip-move";
import CommunityPost from "./CommunityPost";

function CommunityPosts(props) {

    const {posts} = props;
    return (
        <Grid item xs={12} md={8}>
            <div style={{backgroundColor: "white", margin: 10}}>
                {/*<Typography variant="h4" align="center" gutterBottom style={{fontWeight: 600}}>
                    Posts
                </Typography>*/}

                <FlipMove>
                    {posts.map((post) => (
                        <CommunityPost
                            key={post.id}
                            id={post.id}
                            displayName={post.postedBy.username}
                            username={post.postedBy.username}
                            verified={true}
                            text={post.postTitle}
                            avatar={post.postedBy.pplink}
                        />
                    ))}
                </FlipMove>
            </div>
        </Grid>
    );
}

CommunityPosts.propTypes = {
    posts: PropTypes.array.isRequired,
};

export default CommunityPosts;
