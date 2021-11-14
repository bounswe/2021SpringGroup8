import React, { Component } from "react";
import { Redirect } from 'react-router-dom';
import { connect } from "react-redux";



class Profile extends Component {

    render() {
        const { user: currentUser } = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        return (
            <>
                <div className="container">
                    <header className="jumbotron">
                        <h3>
                            <strong>{currentUser.username}</strong> Profile
                        </h3>
                    </header>
                    <p>
                        <strong>Id:</strong> {currentUser.id}
                    </p>
                    <p>
                        <strong>Email:</strong> {currentUser.email}
                    </p>


                </div>
            </>
        );
    }
}

function mapStateToProps(state) {
    const { user } = state.reg;
    return {
        user,
    };
}

export default connect(mapStateToProps)(Profile);
