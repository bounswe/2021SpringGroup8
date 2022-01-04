import React, { Component } from "react";

import UserCommunityService from "../../services/user-community.service";
import { withRouter } from "react-router-dom";
import { Button, Card, Col, Container } from "react-bootstrap";
import { Grid, Tooltip } from "@mui/material";
import Header from "../Components/header/header2";
import Profilebar from "../../components/profilebar";
import SearchIcon from "@mui/icons-material/Search";
import IconButton from "@mui/material/IconButton";
import InputBase from "@mui/material/InputBase";
import ReplayIcon from '@mui/icons-material/Replay';

class Communities extends Component {
    constructor(props) {
        super(props);
        this.state = {
            communities: [],
            activeOption: "Home",
            searchValue: "",
            isLoaded: false,
            isSearchBtnClicked: false,
            filterCommunities: []
        };
    }

    componentDidMount() {
        this.retrieveCommunities();
    }

    retrieveCommunities() {
        UserCommunityService.getCommunities()
            .then((response) => {
                this.setState({
                    communities: response.data["@return"],
                    isLoaded: true,
                });
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e.toString());
            });
    }

    goCommunity(id) {
        console.log(id);
        this.props.history.push("/community/" + id);
    }

    goProfile(id) {
        console.log(id);
        this.props.history.push("/profile/" + id);
    }

    // text box => onchange = {handleChange}
    handleChange(event) {
        this.setState({ searchValue: event.target.value });
    }

    searchButtonClick() {
        const { searchValue } = this.state;
        this.searchCommunity(searchValue);
        this.setState({ isSearchBtnClicked: true })
    }

    clearSearch() {
        this.setState({ isSearchBtnClicked: false })
        document.getElementById('searchInput').value = ''
    }

    searchCommunity(searchText) {
        UserCommunityService.searchCommunity(searchText)
            .then((response) => {
                this.setState({ filterCommunities: response.data["@return"] });

                console.log(response.data);
            })
            .catch((e) => {
                console.log(e.toString());
            });
    }

    displayCommunities() {

        const { communities } = this.state;

        return communities.map((community, index) => (
            <Col md="9">
                <Card style={{ width: "100%" }}>
                    <Card.Img
                        variant="top"
                        src={"https://picsum.photos/" + index + "400/200"}
                        height={200}
                    />
                    <Card.Body>
                        <Card.Title
                            onClick={this.goCommunity.bind(this, community.id)}
                        >
                            {community.CommunityTitle}
                        </Card.Title>
                        <Card.Subtitle
                            onClick={this.goProfile.bind(
                                this,
                                community.createdBy.id
                            )}
                        >
                            {" "}
                            {community.createdBy.username}{" "}
                        </Card.Subtitle>
                        <Card.Text>
                            {community.description}
                        </Card.Text>
                        <Button
                            variant="primary"
                            onClick={this.goCommunity.bind(this, community.id)}
                        >
                            Go to Community
                        </Button>
                    </Card.Body>
                </Card>
            </Col>
        ))
    }
    displaySearchResult() {

        const { filterCommunities } = this.state;

        if (filterCommunities.length === 0) {
            return (
                <Grid item xs={12} md={8}>
                    <div>No result found</div>
                </Grid>
            )
        } else {
            return filterCommunities.map((community, index) => (
                <Col md="9">
                    <Card style={{ width: "100%" }}>
                        <Card.Img
                            variant="top"
                            src={"https://picsum.photos/" + index + "400/200"}
                            height={200}
                        />
                        <Card.Body>
                            <Card.Title
                                onClick={this.goCommunity.bind(this, community.id)}
                            >
                                {community.CommunityTitle}
                            </Card.Title>
                            <Card.Subtitle
                                onClick={this.goProfile.bind(
                                    this,
                                    community.createdBy.id
                                )}
                            >
                                {" "}
                                {community.createdBy.username}{" "}
                            </Card.Subtitle>
                            <Card.Text>
                                {community.description}
                            </Card.Text>
                            <Button
                                variant="primary"
                                onClick={this.goCommunity.bind(this, community.id)}
                            >
                                Go to Community
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            ))
        }


    }



    render() {
        const { filterCommunities, isSearchBtnClicked } = this.state;

        // const filteredCommunities = communities.filter((item) => item.CommunityTitle.toLowerCase().includes(searchValue.toLowerCase()) || item.createdBy.username.toLowerCase().includes(searchValue.toLowerCase()));
        console.log(filterCommunities)
        return (
            <>
                <Profilebar />
                <Header />

                <Container fluid="md">
                    <header className="jumbotron">
                        <h3>
                            <strong>Enjoy various amazing communities</strong>
                        </h3>
                    </header>
                    <Grid item xs={12} md={8}>
                        <InputBase
                            id="searchInput"
                            onChange={this.handleChange.bind(this)}
                            sx={{ ml: 1, flex: 1 }}
                            placeholder="Search Community"
                        />
                        <Tooltip title="Search Community">
                            <IconButton
                                type="submit"
                                sx={{ p: "10px" }}
                                aria-label="search"
                                onClick={this.searchButtonClick.bind(this)}
                            >
                                <SearchIcon />
                            </IconButton>
                        </Tooltip>
                        <Tooltip title="Clear Search">
                            <IconButton
                                type="submit"
                                sx={{ p: "10px" }}
                                aria-label="search"
                                onClick={this.clearSearch.bind(this)}
                            >
                                <ReplayIcon />
                            </IconButton>
                        </Tooltip>
                    </Grid>

                    {isSearchBtnClicked ? this.displaySearchResult() : this.displayCommunities()}
                </Container>
            </>
        );
    }
}

export default withRouter(Communities);
