import './App.css';
import React, { Component } from "react";
import {Router, Switch, Route, withRouter} from "react-router-dom";
import { connect } from "react-redux";
import Login from "./public-content/pages/login";
import Register from "./public-content/pages/Register";
import Post from "./components/post"
import PostListComponent from "./components/posts";
import HomePage from "./public-content/pages/HomePage";
import Profile from "./pages/profile";

import Welcome from "./public-content/pages/welcome";
import Communities from "./public-content/pages/Communities";
import Community from "./public-content/pages/Community";
import home from "./public-content/pages/home";
import PostView from "./public-content/PostView";
import CreateDataType from "./public-content/pages/CreateDataType";
import CreatePost from "./public-content/pages/CreatePost";
import { history } from './helpers/history';
class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {

    };

  }

  componentDidMount() {

  }
  componentWillUnmount() {
  }

  logOut() {
  }

  render() {
    return (
        <Router history={history}>
          <div>
            <>
              <Switch>
                <Route exact path={"/"} component={home}/>
                {/*<Route exact path={"/home"} component={HomePage}/>*/}
                <Route exact path={"/profile"} component={Profile}/>

                <Route exact path={"/posts"} component={PostListComponent} />
                <Route exact path="/post/:id" component={PostView}  />
                <Route exact path="/login" component={Login} />
                <Route exact path={"/signup"} component={Register} />
                <Route exact path={"/communities"} component={Communities} />
                <Route exact path="/samples" component={Register} />
                <Route exact path="/community/:id" component={Community}/>
                <Route exact path="/createDataType/:id" component={CreateDataType}/>
                <Route exact path="/createPost/:id" component={CreatePost}/>

              </Switch>
            </>
          </div>

        </Router>
    );
  }
}

function mapStateToProps(state) {
  const { user } = state.reg;
  return {
    user,
  };
}

export default connect(mapStateToProps)(App);
