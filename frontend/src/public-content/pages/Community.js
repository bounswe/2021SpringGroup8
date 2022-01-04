import React, {Component} from "react";
import CloseIcon from '@mui/icons-material/Close';
import UserCommunityService from "../../services/user-community.service";
import UserService from "../../services/user.service";
import AuthService from "../../services/auth.service";
import {withRouter} from "react-router-dom";
import {Avatar, Container, Grid} from "@mui/material";
import CommunityAbout from "../Components/CommunityAbout";
import CommunityPosts from "../Components/CommunityPosts";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Toolbar from "@mui/material/Toolbar";
import userCommunityService from "../../services/user-community.service";
import BasicSpeedDial from "../Components/BasicSpeedDial";
import Profilebar from "../../components/profilebar";
import SearchIcon from "@mui/icons-material/Search";
import IconButton from "@mui/material/IconButton";
import ManageSearchIcon from '@mui/icons-material/ManageSearch';

import InputBase from "@mui/material/InputBase";
import AdvancedSearchInCommunity from "../Components/AdvancedSearchInCommunity";

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
            dataTypes: [],
            variant: "contained",
            advancedSearchOpen: false,
            text: "Subscribe",
            owner: false,
            id: "",
            searchValue: "",
            isLoaded: false,
        };
        this.subscribe = this.subscribe.bind(this);
    }

    componentDidMount() {
        console.log(this.props.match.params.id);
        this.retrieveCommunity(this.props.match.params.id);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        this.retrieveCommunity();
    }

    handleChange(event) {
        this.setState({searchValue: event.target.value});
    }

    retrieveUsersCommunities() {
        let users_communities = [];
        UserService.getUsersCommunities()
            .then((response) => {
                if (response.data["@success"] === "True") {
                    let users_communities = response.data["@return"].subscribes;
                    console.log(users_communities);
                } else {
                    console.log(response.data["@error"]);
                }
            })
            .catch((error) => {
                console.log(error);
            });
        console.log(users_communities);
    }

    checkSubscribeStatus() {
        const myUser = AuthService.getCurrentUser();
        if (this.state.subscribers.some((user) => user.id === myUser.id)) {
            this.setState({
                subscribed: true,
                variant: "outlined",
                text: "Unsubscribe",
            });
        } else {
            this.setState({
                subscribed: false,
                variant: "contained",
                text: "Subscribe",
            });
            console.log("not subscribed yet");
        }
    }

    subscribe() {
        if (this.state.subscribed) {
            userCommunityService
                .unsubscribeCommunity(this.props.match.params.id)
                .then((response) => {
                    if (response.data["@success"] === "True") {
                        this.setState({
                            subscribed: false,
                            variant: "contained",
                            text: "Subscribe",
                        });
                    } else {
                        console.log(response.data["@error"]);
                        alert(response.data["@error"]);
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
        } else {
            userCommunityService
                .subscribeCommunity(this.props.match.params.id)
                .then((response) => {
                    console.log(response.data);
                    if (response.data["@success"] === "True") {
                        this.setState({
                            subscribed: true,
                            variant: "outlined",
                            text: "Unsubscribe",
                        });
                    } else {
                        console.log(response.data["@error"]);
                        alert(response.data["@error"]);
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    }

    retrieveCommunity(id) {
        UserCommunityService.getCommunityById(id)
            .then((response) => {
                console.log(response)
                this.setState({
                    communityTitle: response.data["@return"]["communityTitle"],
                    description: response.data["@return"]["description"],
                    creator: response.data["@return"]["createdBy"]["username"],
                    subscribers: response.data["@return"]["subscribers"],
                    posts: response.data["@return"]["posts"],
                    id: id,
                    dataTypes: response.data["@return"]["dataTypes"],
                    isLoaded: true,
                });
                this.checkSubscribeStatus();
                const myUser = AuthService.getCurrentUser();
                if (response.data["@return"].createdBy.id === myUser.id) {
                    this.setState({owner: true});
                }
                console.log(response.data["@return"]);
            })
            .catch((e) => {
                console.log(e.toString());
            });
    }

    openAdvancedSearch() {
        if (this.state.advancedSearchOpen) {
            this.setState({advancedSearchOpen: false})
        } else {
            this.setState({advancedSearchOpen: true})
        }

    }

    render() {
        const {
            posts,
            owner,
            creator,
            description,
            communityTitle,
            variant,
            text,
            advancedSearchOpen,
            searchValue,
            isLoaded,
            id,
            dataTypes
        } = this.state;
        console.log(advancedSearchOpen)

        const filterPosts = posts.filter((item) => item.postTitle.toLowerCase().includes(searchValue.toLowerCase()) ||
            item.dataTypeName.toLowerCase().includes(searchValue.toLowerCase()) ||
            item.postedBy.username.toLowerCase().includes(searchValue.toLowerCase())
        );

        return (
            <>
                <Profilebar/>
                {isLoaded ? (<Container maxWidth="md">
                    <Toolbar
                        sx={{height: "100px", borderBottom: 1, borderColor: "divider"}}
                    >
                        <Avatar
                            alt={communityTitle}
                            src="https://picsum.photos/200/200"
                            sx={{width: 100, height: 100}}
                        />
                        <div style={{marginLeft: 10}}>
                            <Button
                                id="subscribe"
                                size="Large"
                                variant={variant}
                                onClick={this.subscribe}
                            >
                                {text}
                            </Button>
                        </div>
                        <Typography
                            component="h2"
                            variant="h5"
                            color="inherit"
                            align="center"
                            sx={{flex: 1}}
                        >
                            {communityTitle}
                        </Typography>
                    </Toolbar>
                    <InputBase
                        onChange={this.handleChange.bind(this)}
                        sx={{ml: 1, flex: 1}}
                        placeholder="Search Community"
                    />
                    <IconButton
                        type="submit"
                        sx={{p: "10px"}}
                        aria-label="search"
                    >
                        <SearchIcon/>
                    </IconButton>

                    <IconButton
                        onClick={this.openAdvancedSearch.bind(this)}
                        aria-label="advanced search"
                        aria-valuetext="advanced search"
                    >
                        {advancedSearchOpen ? (<CloseIcon/>) : (<ManageSearchIcon/>)}
                    </IconButton>

                    <Grid container direction="row">
                        <Grid item xs={8}>
                            {isLoaded ? (
                                <div>
                                    {advancedSearchOpen ? (
                                        <AdvancedSearchInCommunity communityID={id} dataTypes={dataTypes}/>
                                    ) : (
                                        <CommunityPosts description="something" posts={filterPosts}/>
                                    )}
                                </div>

                            ) : (
                                <div>Loading...</div>
                            )}
                        </Grid>
                        <Grid item xs={4}>
                            <CommunityAbout
                                description={description}
                                moderators={creator}
                                tags="soccer football "
                            />
                        </Grid>

                    </Grid>
                    <BasicSpeedDial
                        owner={owner}
                        communityId={this.props.match.params.id}
                    />
                </Container>) : (<Grid item xs={12} md={8}>
                    <div>Loading...</div>
                </Grid>)}
            </>
        );
    }
}

export default withRouter(Community);
