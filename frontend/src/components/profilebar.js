import {history} from "../helpers/history";

import EventBus from "../common/EventBus";
import {logout} from "../actions/register";
import {Link, Route, Router, Switch} from "react-router-dom";
import React, {Component} from "react";
import {connect} from "react-redux";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "../App.css";
import logo from "../public-content/images/logo_t.png";


class Profilebar extends Component{

    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);

        this.state = {
            currentUser: undefined,
        };


    }

    componentDidMount() {
        const user = this.props.user;

        if (user) {
            this.setState({
                currentUser: user,
            });
        }

        EventBus.on("logout", () => {
            this.logOut();
        });


    }

    componentWillUnmount() {
        EventBus.remove("logout");
    }

    logOut() {
        this.props.dispatch(logout());
        this.setState({
            currentUser: undefined,
        });
    }

    render() {
        const {currentUser} = this.state;

        return (
            <Router history={history}>
                <div>
                    <nav className="navbar navbar-expand navbar-light bg-secondary">
                        <Link to={"/"} className="navbar-brand">
                            <img src={logo} width="150"
                                 height="100"/>
                        </Link>
                        {/*{currentUser && (*/}
                        {/*<Link to="/communities" className="nav-link">*/}
                        {/*    Communities*/}
                        {/*</Link>)}*/}
                        {currentUser ? (
                            <div className="navbar-nav ml-auto">
                                {/*<li className="nav-item">*/}
                                {/*    <Link to={"/communities"} className="nav-link">*/}
                                {/*        Communities*/}
                                {/*    </Link>*/}
                                {/*</li>*/}
                                {/*<li className="nav-item">*/}
                                {/*    <Link to={"/feed"} className="nav-link">*/}
                                {/*        Home Feed*/}
                                {/*    </Link>*/}
                                {/*</li>*/}
                                <li className="nav-item">
                                    <Link to={"/profile"} className="nav-link">
                                        {currentUser.username}
                                    </Link>
                                </li>
                                <li className="nav-item">
                                    <Link to={"/login"} className="nav-link" onClick={this.logOut}>
                                        Log Out
                                    </Link>
                                </li>
                            </div>
                        ) : (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={"/login"} className="nav-link">
                                        Login
                                    </Link>
                                </li>

                                <li className="nav-item">

                                </li>
                            </div>
                        )}
                    </nav>


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

export default connect(mapStateToProps)(Profilebar);
