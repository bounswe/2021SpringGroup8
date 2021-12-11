import React, {Component} from "react";
import "./styles/Sidebar.css";
import TwitterIcon from "@material-ui/icons/Twitter";
import SidebarOption from "./SiderbarOption";
import SubSidebarOption from "./SubSideBarOption";
import HomeIcon from "@material-ui/icons/Home";
import SearchIcon from "@material-ui/icons/Search";
import NotificationsNoneIcon from "@material-ui/icons/NotificationsNone";
import MailOutlineIcon from "@material-ui/icons/MailOutline";
import BookmarkBorderIcon from "@material-ui/icons/BookmarkBorder";
import ListAltIcon from "@material-ui/icons/ListAlt";
import PermIdentityIcon from "@material-ui/icons/PermIdentity";
import MoreHorizIcon from "@material-ui/icons/MoreHoriz";
import {Button} from "@material-ui/core";
import UserCommunityService from "../../services/user-community.service";
import {withRouter} from 'react-router-dom';


class Sidebar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            communities: [],
            activeOption: "Home"
        }
    }

    componentDidMount() {
        this.retrieveUserCommunities();
    }

    retrieveUserCommunities() {
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

    render() {
        const {communities, activeOption} = this.state

        const handleClick = (event) => {
            console.log(event.target.textContent)
            if(event.target.textContent === "Profile"){
                this.props.history.push("/profile");
            }
            this.setState({
                    activeOption: event.target.textContent
                }
            );
        }
        return (
            <div className="sidebar">
                <TwitterIcon className="sidebar__twitterIcon"/>

                <SidebarOption active={activeOption === "Home"} Icon={HomeIcon} text="Home" onClick={handleClick}/>
                <SidebarOption active={activeOption === "Explore"} Icon={SearchIcon} text="Explore" onClick={handleClick}/>
                <SidebarOption active={activeOption === "Communities"} Icon={NotificationsNoneIcon} text="Communities" onClick={handleClick}/>
                {communities && communities.map((community, index) => (
                    <SubSidebarOption active={activeOption === community.CommunityTitle} Icon={NotificationsNoneIcon} text={community.CommunityTitle} onClick={handleClick}/>
                ))}
                <SidebarOption active={activeOption === "Messages"} Icon={MailOutlineIcon} text="Messages" onClick={handleClick}/>
                {/*<SidebarOption Icon={BookmarkBorderIcon} text="Bookmarks"/>*/}
                <SidebarOption active={activeOption === "Lists"} Icon={ListAltIcon} text="" onClick={handleClick}/>
                <SidebarOption active={activeOption === "Profile"} Icon={PermIdentityIcon} text="Profile" onClick={handleClick}/>
                <SidebarOption active={activeOption === "More"} Icon={MoreHorizIcon} text="More" onClick={handleClick}/>

                {/* Button -> Tweet */}
                <Button variant="outlined" className="sidebar__tweet" fullWidth>
                    Share
                </Button>
            </div>
        );
    }

}
export default withRouter(Sidebar);
