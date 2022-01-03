import * as React from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import { Grid, Card, Paper } from "@mui/material";
import Post from "./Post";
import CommunityPost from "./CommunityPost";

function CommunityPosts(props) {

    const { posts } = props;
    if (posts.length === 0) {
        return (
            <Grid item xs={12} md={8}>
                <div>No result found</div>
            </Grid>
        )
    }
    else {
        return (
            <Grid item xs={12} md={8}>
                <div style={{ backgroundColor: "white", margin: 10 }}>
                    {/*<Typography variant="h4" align="center" gutterBottom style={{fontWeight: 600}}>
                    Posts
                </Typography>*/}

                    <Card>
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
                    </Card>
                </div>
            </Grid>
        );
    }
}

CommunityPosts.propTypes = {
    posts: PropTypes.array.isRequired,
};

export default CommunityPosts;
