import React, { Component } from "react";
import { Redirect } from 'react-router-dom';
import { connect } from "react-redux";
import Profilebar from "../components/profilebar";



class Profile extends Component {



    render() {
        const { user: currentUser } = this.props;

        if (!currentUser) {
            return <Redirect to="/login"/>;
        }

        return (
            <>
                <Profilebar/>
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
                    <p>
                        <strong>Name:</strong> {currentUser.name}
                    </p>
                    <p>
                        <strong>Surname:</strong> {currentUser.surname}
                    </p>
                    <p>
                        <strong>City:</strong> {currentUser.city}
                    </p>
                    <p>
                        <strong>Birth Date:</strong> {new Date(currentUser.birthdate._isoformat).toLocaleDateString()}
                    </p>
                    <p>
                        <strong>Profile Picture:</strong> {currentUser.pplink}
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
