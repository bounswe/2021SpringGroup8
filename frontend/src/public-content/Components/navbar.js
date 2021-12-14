import React, { Component } from "react";
import { Link } from "react-router-dom";
import { FaAlignRight } from "react-icons/fa";
import logo from "../images/logo_t.png";
import "../../App.css";
export default class Navbar extends Component {
    state = {
        isOpen: false
    };
    handleToggle = () => {
        this.setState({ isOpen: !this.state.isOpen });
    };
    render() {
        return (
            <nav className="navbar">
                <div className="nav-center">
                    <div className="nav-header">
                        <Link to="/">
                            <img src={logo} width="200"
                                 height="120" alt="alternative txt" />
                        </Link>
                        <button
                            type="button"
                            className="nav-btn"
                            onClick={this.handleToggle}
                        >
                            <FaAlignRight className="nav-icon" />
                        </button>
                    </div>
                    <ul
                        className={this.state.isOpen ? "nav-links show-nav" : "nav-links"}
                    >
                        {/*<li>*/}
                        {/*    <Link to="/home">Home</Link>*/}
                        {/*</li>*/}

                        <li>
                            <Link to="/communities">Communities</Link>
                        </li>
                        <li>
                            <Link to="/Signup">Sign Up</Link>
                        </li>
                        <li>
                            <Link to="/Login">Login</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }
}
