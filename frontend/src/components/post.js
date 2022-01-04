import React, {Component} from "react";

import UserPostService from "../services/user-post.service";

export default class Post extends Component {
    constructor(props) {
        super(props);
        this.onChangeTopic = this.onChangeTopic.bind(this);
        this.onChangeBody = this.onChangeBody.bind(this);
        this.getPosts = this.getPosts.bind(this);
        this.getPost = this.getPost.bind(this);

        this.state = {
            currentPost: {
                postid: null,
                topic: "",
                body: "",
                sender_username: "",
                new: true,
            },
            post: ""
        };
    }

    componentDidMount() {
        this.getPost(this.props.match.params.messageid);
    }

    onChangeTopic(e) {
        const topic = e.target.value;
        this.setState(function (prevState) {
            return {
                currentMessage: {
                    ...prevState.currentMessage,
                    topic: topic,
                }
            };
        });
    }

    onChangeBody(e) {
        const body = e.target.value;

        this.setState(prevState => ({
            currentPost: {
                ...prevState.currentPost,
                body: body
            }
        }));
    }

    getPost(postid) {
        UserPostService.getPostById(postid)
            .then(response => {
                this.setState({
                    currentPost: response.data
                });
                console.log("response from backend for the post with desired id is ");
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {currentPost} = this.state;
        console.log("current message is ");
        console.log(currentPost);

        return (
            <div>
                {currentPost ? (
                    <div className="edit-form">
                        <h4>Post</h4>
                        <form>
                            <div className="form-group">
                                <label htmlFor="topic">Topic</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="topic"
                                    value={currentPost.topic}
                                    onChange={this.onChangeTopic}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="body">Body</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="body"
                                    value={currentPost.body}
                                    onChange={this.onChangeBody}
                                />
                            </div>

                            <div className="form-group">
                                <label>
                                    <strong>Status:</strong>
                                </label>
                                {currentPost.new ? "New post" : "Old post"}
                            </div>
                        </form>

                        <p>{this.state.message}</p>
                    </div>
                ) : (
                    <div>
                        <br/>
                        <p>Please click on a post...</p>
                    </div>
                )}
            </div>
        );
    }


}
