import './App.css';
import React, { Component } from "react";
import { Switch, Route } from "react-router-dom";
import { connect } from "react-redux";
import Login from "./public-content/pages/login";
import ValidatingSignUpForm from "./public-content/pages/ValidatingSignUpForm";
import Post from "./components/post"
import PostListComponent from "./components/posts";
import HomePage from "./public-content/pages/HomePage";
import Profile from "./pages/profile";
import Welcome from "./public-content/pages/welcome";
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
          <div>
            <>
              <Switch>
                <Route exact path={"/"} component={Welcome}/>
                <Route exact path={"/home"} component={HomePage}/>
                <Route exact path={"/profile"} component={Profile}/>
                <Route exact path={"/posts"} component={PostListComponent} />
                <Route path="/post/:id" component={Post} />
                <Route exact path="/login" component={Login} />
                <Route exact path={"/signup"} component={ValidatingSignUpForm} />
              </Switch>
            </>
          </div>
    );
  }
}

function mapStateToProps(state) {
}

export default connect(mapStateToProps)(App);
