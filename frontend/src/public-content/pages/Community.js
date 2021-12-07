import React, {Component} from "react";

import UserCommunityService from "../../services/user-community.service";
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
        }
    }

    componentDidMount() {
        console.log(this.props.match.params.id);
        this.retrieveCommunity();
    }

    retrieveCommunity() {
        UserCommunityService.getCommunityById(this.props.match.params.id)
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
        const {posts,subscribers,creator,description,communityTitle } = this.state

        return (
            <Container maxWidth="md">
                <CommunityHeader title={communityTitle} name=""/>
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
