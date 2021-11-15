import React, { Component } from "react";
import { Link } from "react-router-dom";
import { FaAlignRight } from "react-icons/fa";

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
                        <li>
                            <Link to="/home">Home</Link>
                        </li>

                        <li>
                            <Link to="/posts">Community</Link>
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
