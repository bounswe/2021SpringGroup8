import React, {Component} from "react";
import Sidebar from "../Components/Sidebar";
import "../styles.css"
import Feed from "../Components/Feed";
import {Redirect} from "react-router-dom";
import {connect} from "react-redux";


class HomePage extends Component{

    render() {

        const { user: currentUser } = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }
        return (
            <div className="home_page">
                <Sidebar/>
                <Feed/>

            </div>
        );
    }

}

function mapStateToProps(state) {
    const { user } = state.reg;
    return {
        user,
    };
}

export default connect(mapStateToProps)(HomePage);

