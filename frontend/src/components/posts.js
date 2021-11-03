import React, { Component } from "react";
import UserPostService from "../services/user-post.service.js";
import { Link } from "react-router-dom";


export default class PostListComponent extends Component{

    constructor(props) {
        super(props);
        this.retrievePosts = this.retrievePosts.bind(this);
        this.refreshList = this.refreshList.bind(this); //refresh functionality
        this.setActivePost = this.setActivePost.bind(this);  // update the tutorial
        this.searchTopic = this.searchTopic.bind(this); //search functionality
        this.onChangeSearchTopic = this.onChangeSearchTopic.bind(this);  //instantaneous(reactive) search functionality
        this.state = {
            posts:[],
            currentPost:null,
            currentIndex : -1,
            searchTopic:""

        }
    }

    componentDidMount() {
        this.retrievePosts();
    }

    refreshList() {
        this.retrievePosts();
        this.setState({
            currentPost: null,
            currentIndex: -1
        });
    }
    retrievePosts() {
        UserPostService.getPosts()
            .then(response => {
                this.setState({
                    posts: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    setActivePost(post, index) {
        this.setState({
            currentPost: post,
            currentIndex: index
        });
    }

    searchTopic() {
        this.setState({
            currentPost: null,
            currentIndex: -1
        });

        UserPostService.getPostByTitle(this.state.searchTopic)
            .then(response => {
                this.setState({
                    posts: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChangeSearchTopic(e) {
        const searchTopic = e.target.value;

        this.setState({
            searchTopic: searchTopic
        });
    }

    render() {
        const { searchTopic, posts, currentPost, currentIndex } = this.state;

        return (
            <>
                <div className="list row">
                    <div className="col-md-8">
                        <div className="input-group mb-3">
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Search by title"
                                value={searchTopic}
                                onChange={this.onChangeSearchTopic}
                            />
                            <div className="input-group-append">
                                <button
                                    className="btn btn-outline-secondary"
                                    type="button"
                                    onClick={this.searchTopic}
                                >
                                    Search
                                </button>
                            </div>
                        </div>
                    </div>
                    <div className="col-md-8">
                        <Link to="/createNewPost">Create a new post by admin</Link>
                    </div>
                    <div className="col-md-6">
                        <h4>Community Post List</h4>

                        <ul className="list-group">
                            {posts &&
                            posts.map((post, index) => (
                                <li
                                    className={
                                        "list-group-item " +
                                        (index === currentIndex ? "active" : "")
                                    }
                                    onClick={() => this.setActivePost(post, index)}
                                    key={index}
                                >
                                    {post.topic}
                                </li>
                            ))}
                        </ul>

                    </div>
                    <div className="col-md-6">
                        {currentPost ? (
                            <div>
                                <h4>Post</h4>
                                <div>
                                    <label>
                                        <strong>Topic:</strong>
                                    </label>{" "}
                                    {currentPost.topic}
                                </div>
                                <div>
                                    <label>
                                        <strong>Body:</strong>
                                    </label>{" "}
                                    {currentPost.body}
                                </div>
                                <div>
                                    <label>
                                        <strong>Sender Username:</strong>
                                    </label>{" "}
                                    {currentPost.sender_username}
                                </div>
                                <div>
                                    <label>
                                        <strong>Date sent :</strong>
                                    </label>{" "}
                                    To be implemented...
                                </div>
                                <div>
                                    <label>
                                        <strong>Status:</strong>
                                    </label>{" "}
                                    {currentPost.new ? "New post" : "Old post"}
                                </div>

                            </div>
                        ) : (
                            <div>
                                <br />
                                <p>Please click on a post...</p>
                            </div>
                        )}
                    </div>
                </div>

            </>
        );
    }




}