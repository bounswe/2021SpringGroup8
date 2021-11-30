import React, {Component} from "react";

import UserCommunityService from "../../services/user-community.service";
import {withRouter} from 'react-router-dom';
import {Button, Card, Col, Container} from "react-bootstrap";


class Communities extends Component {

    constructor(props) {
        super(props);
        this.state = {
            communities: [],
            activeOption: "Home"
        }
    }

    componentDidMount() {
        this.retrieveCommunities();
    }

    retrieveCommunities() {
        UserCommunityService.getCommunities()
            .then(response => {
                this.setState({
                    communities: response.data['@return']
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e.toString());
            });
    }

    goCommunity(id) {
        console.log(id);
        this.props.history.push("/communities/" + id);
    }

    goProfile(id) {
        console.log(id);
        this.props.history.push("/profile/" + id);
    }

    render() {
        const {communities} = this.state

        return (
            <Container fluid="md">
                {communities && communities.map((community, index) => (
                    <Col md="9">
                        <Card style={{width: "100%"}}>
                            <Card.Img variant="top" src="holder.js/100px180"/>
                            <Card.Body >
                                <Card.Title
                                    onClick={this.goCommunity.bind(this, community.id)}>{community.CommunityTitle}</Card.Title>
                                <Card.Subtitle onClick={
                                    this.goProfile.bind(this, community.createdBy.id)}> {community.createdBy.username} </Card.Subtitle>
                                <Button variant="primary" onClick={this.goCommunity.bind(this, community.id)}>Go to Community</Button>
                            </Card.Body>
                        </Card>
                    </Col>

                ))}
            </Container>
        );
    }

}

export default withRouter(Communities);
