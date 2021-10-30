import './App.css';
import React, { Component } from "react";
import { Switch, Route } from "react-router-dom";
import { connect } from "react-redux";
import Login from "./public-content/pages/login";
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
                <Route exact path="/login" component={Login} />
              </Switch>
            </>
          </div>
    );
  }
}

function mapStateToProps(state) {
}

export default connect(mapStateToProps)(App);