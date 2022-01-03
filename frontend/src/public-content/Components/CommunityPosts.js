import * as React from 'react';
import PropTypes from 'prop-types';
import {Grid, Card, Paper} from "@mui/material";
import CommunityPost from "./CommunityPost";
import Box from "@mui/material/Box";

function CommunityPosts(props) {

    const {posts} = props;
    if (posts.length === 0) {
        return (
                <Box sx={{ width: '100%' }}>No Results Found</Box>
        )
    } else {
        return (
            <div>
                <div style={{backgroundColor: "white", margin: 10}}>

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
            </div>
        );
    }
}

CommunityPosts.propTypes = {
    posts: PropTypes.array.isRequired,
};

export default CommunityPosts;
