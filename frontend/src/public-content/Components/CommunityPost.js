import React from "react";
import "./styles/Post.css";
import {Avatar} from "@material-ui/core";
import VerifiedUserIcon from "@material-ui/icons/VerifiedUser";

import {useHistory} from "react-router-dom";

function CommunityPost(props) {
    const {id, displayName, username, verified, text} = props
    let history = useHistory();

    function goToPost() {
        history.push('/post/' + id)
    }

    return (
        <div className="post" onClick={goToPost}
             style={{cursor: 'pointer', backgroundColor: '#F2F8FC', marginBottom: 15}}>
            <div className="post__avatar">
                <Avatar src="https://picsum.photos/100/100"/>
            </div>
            <div className="post__body">
                <div className="post__header">
                    <div className="post__headerText">
                        <h3>
                            {displayName}{" "}
                            <span className="post__headerSpecial">
                  {verified && <VerifiedUserIcon className="post__badge"/>} @
                                {username}
                </span>
                        </h3>
                    </div>
                    <div className="post__headerDescription">
                        <p>{text}</p>
                    </div>
                </div>
                <img src="https://picsum.photos/400/200" alt=""/>
            </div>
        </div>
    );
}


export default CommunityPost;
