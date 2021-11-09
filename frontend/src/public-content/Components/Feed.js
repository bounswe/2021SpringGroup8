import React, { useState } from "react";
import TweetBox from "./TweetBox";
import Post from "./Post";
import "./styles/Feed.css";
import FlipMove from "react-flip-move";

function Feed() {
    const [posts] = useState([]);


    return (
        <div className="feed">
            <div className="feed__header">
                <h2>Home</h2>
            </div>

            <TweetBox />

            <FlipMove>
                {posts.map((post) => (
                    <Post
                        key={post.text}
                        displayName={post.displayName}
                        username={post.username}
                        verified={post.verified}
                        text={post.text}
                        avatar={post.avatar}
                        image={post.image}
                    />
                ))}
            </FlipMove>
        </div>
    );
}

export default Feed;
