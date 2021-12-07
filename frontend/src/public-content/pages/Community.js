import React, {Component} from "react";

import UserCommunityService from "../../services/user-community.service";
import UserService from "../../services/user.service";
import {withRouter} from 'react-router-dom';
import {Button, Card, Col} from "react-bootstrap";
import CommunityHeader from "../Components/CommunityHeader";
import {Container, Grid} from "@mui/material";
import CommunityAbout from "../Components/CommunityAbout";
import CommunityPosts from "../Components/CommunityPosts";

class Community extends Component {

    constructor(props) {
        super(props);
        this.state = {
            posts: [],
            activeOption: "Home",
            title: "example-title",
            description: "example-description",
            creator: "example-creator",
            subscribers: [],
            subscribed: false,
        }
    }

    componentDidMount() {
        console.log(this.props.match.params.id);
        this.retrieveCommunity(this.props.match.params.id);
        this.retrieveUsersCommunities();
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        this.retrieveUsersCommunities();
    }

    retrieveUsersCommunities() {
        let users_communities = []
        UserService.getUsersCommunities().then(response => {
            if (response.data['@success'] === "True") {
                console.log(response.data['@return'].subscribers)
                let users_communities = response.data['@return'].subscribers
                for ( let community in users_communities) {
                    if (community.id === this.props.match.params.id ) {
                        this.setState({subscribed: true})
                    }
                }
            } else {
                console.log(response.data['@error'])
            }
        }).catch(error => {
            console.log(error)
        });
        console.log(users_communities);

    }

    retrieveCommunity(id) {
        UserCommunityService.getCommunityById(id)
            .then(response => {
                this.setState({
                    communityTitle: response.data['@return']["communityTitle"],
                    description: response.data['@return']["description"],
                    creator: response.data['@return']["createdBy"]["username"],
                    subscribers: response.data['@return']["subscribers"],
                    posts: response.data['@return']["posts"]
                });
                console.log(response.data['@return']);
            })
            .catch(e => {
                console.log(e.toString());
            });
    }

    render() {
        const {posts,subscribers,creator,description,communityTitle,subscribed } = this.state

        return (
            <Container maxWidth="md">
                <CommunityHeader id={this.props.match.params.id} title={communityTitle} subscribed={subscribed}/>
                <Grid container direction="row">
                    <CommunityPosts description="something" posts={posts}/>
                    <CommunityAbout description={description} moderators={creator}
                                    tags="soccer football "/>
                </Grid>
            </Container>
        );
    }

}

export default withRouter(Community);
