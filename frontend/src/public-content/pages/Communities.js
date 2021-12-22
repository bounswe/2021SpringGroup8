import React, { Component } from "react";

import UserCommunityService from "../../services/user-community.service";
import { withRouter } from "react-router-dom";
import { Button, Card, Col, Container } from "react-bootstrap";
import {Grid} from "@mui/material";
import Header from "../Components/header/header2";
import Profilebar from "../../components/profilebar";
import SearchIcon from "@mui/icons-material/Search";
import IconButton from "@mui/material/IconButton";
import InputBase from "@mui/material/InputBase";

class Communities extends Component {
  constructor(props) {
    super(props);
    this.state = {
      communities: [],
      activeOption: "Home",
      searchValue: "",
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

  //button => onClick = {onClick}
  onClick() {
    // api call or filter
    window.alert(this.state.searchValue);
  }

  render() {
    const { communities } = this.state;

    return (
      <>
        <Profilebar />
        <Header />
        <Container fluid="md">
          <Grid item xs={12} md={8}>
            <InputBase
              onChange={this.handleChange.bind(this)}
              sx={{ ml: 1, flex: 1 }}
              placeholder="Search Community"
            />
            <IconButton
              type="submit"
              sx={{ p: "10px" }}
              aria-label="search"
              onClick={this.onClick.bind(this)}
            >
              <SearchIcon />
            </IconButton>
          </Grid>
          <header className="jumbotron">
            <h3>
              <strong>Enjoy various amazing communities</strong>
            </h3>
          </header>
          {communities &&
            communities.map((community, index) => (
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
                    <Button
                      variant="primary"
                      onClick={this.goCommunity.bind(this, community.id)}
                    >
                      Go to Community
                    </Button>
                  </Card.Body>
                </Card>
              </Col>
            ))}
        </Container>
      </>
    );
  }
}

export default withRouter(Communities);
