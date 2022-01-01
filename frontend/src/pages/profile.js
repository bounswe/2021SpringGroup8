import React, { Component } from "react";
import {Redirect, withRouter} from 'react-router-dom';
import { connect } from "react-redux";
import Profilebar from "../components/profilebar";
import querystring from "querystring";
import Navbar from "../public-content/Components/navbar";
import Footer from "../public-content/Components/footer/footer";
import UserCommunityService from "../services/user-community.service";
import ProfileService from "../services/profile.service";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { Link } from "react-router-dom";



class Profile extends Component {

    constructor(props) {
        super(props);
        this.createCommunity = this.createCommunity.bind(this);
        this.editEmail = this.editEmail.bind(this);
        this.onChangeCommunityTitle = this.onChangeCommunityTitle.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.setActiveCreationReq = this.setActiveCreationReq.bind(this);
        this.setActiveCreationReqNonActive = this.setActiveCreationReqNonActive.bind(this);
        this.setActiveEmailEdit = this.setActiveEmailEdit.bind(this);
        this.setActiveEmailEditFalse = this.setActiveEmailEditFalse.bind(this);
        this.state = {
            communityTitle: "",
            description: "",
            loading: false,
            communityCreationReq: true,
            emailEdit : true,
            email:"",
        };

    }
    setActiveEmailEdit() {
        this.setState({
            emailEdit: true,

        });
    }
    setActiveEmailEditFalse() {
        this.setState({
            emailEdit: false,

        });
    }
    setActiveCreationReq() {
        this.setState({
            communityCreationReq: false,

        });
    }
    setActiveCreationReqNonActive() {
        this.setState({
            communityCreationReq: true,

        });
    }
    onChangeCommunityTitle(e) {
        this.setState({
            communityTitle: e.target.value,
        });
    }
    onChangeEmail(e) {
        this.setState({
            email: e.target.value,
        });
    }

    onChangeDescription(e) {

        this.setState({
            description: e.target.value,
        });
    }

    createCommunity(e) {
        e.preventDefault();

        this.setState({
            loading: true,
        });

        this.form.validateAll();


        const { history} = this.props;

        if (this.checkBtn.context._errors.length === 0) {
            console.log("Desc is ")
            console.log(this.description)
            UserCommunityService
                .createCommunity(
                    this.state.communityTitle,
                    this.state.description,
                )
                .then(
                    response => {
                        if (response.data['@success'] !== 'False') {
                            console.log(response.data['@return']);

                            history.push('/communities');
                        } else {
                            console.log(response.data['@error']);
                            alert(response.data['@error']);
                        }
                    })
                .catch(
                    error => {
                        const resMessage =
                            (error.response &&
                                error.response.data &&
                                error.response.data.message) ||
                            error.message ||
                            error.toString();
                        console.log(resMessage);
                    }
                );
        } else {
            this.setState({
                loading: false,
            });
        }
    }

    editEmail(e) {
        e.preventDefault();

        this.setState({
            loading: true,
        });

        this.form.validateAll();


        const { history} = this.props;

        if (this.checkBtn.context._errors.length === 0) {
            console.log("email is ")
            console.log(this.email)
            ProfileService
                .updateEmail(
                    this.state.email,

                )
                .then(
                    response => {
                        if (response.data['@success'] !== 'False') {
                            console.log(response.data['@return']);

                            var usr = localStorage.getItem('user')
                            //console.log(usr)
                            //console.log("user['email'] is ")
                            //console.log(this.state.email)
                            localStorage.setItem('user', usr)
                            this.setActiveEmailEdit();

                        } else {
                            console.log(response.data['@error']);
                            alert(response.data['@error']);
                        }
                    })
                .catch(
                    error => {
                        const resMessage =
                            (error.response &&
                                error.response.data &&
                                error.response.data.message) ||
                            error.message ||
                            error.toString();
                        console.log(resMessage);
                    }
                );
        } else {
            this.setState({
                loading: false,
            });
        }
    }



    render() {
        const {communityCreationReq : communityCreationReq , emailEdit: emailEdit} = this.state;

        const { user: currentUser } = this.props;

        const { usertoken: currentToken } = this.props;

        const {message} = this.props;

        console.log(currentToken);

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
                        {/*<strong>Email:</strong> {currentUser.email}*/}
                        {/*<button*/}
                        {/*    onClick={this.setActiveEmailEditFalse}*/}
                        {/*    className="badge badge-warning"*/}
                        {/*>*/}
                        {/*    Edit*/}
                        {/*</button>*/}
                        <div className="col-md-6">
                            { emailEdit ?(
                                <p>
                                <strong>Email:</strong> {currentUser.email}
                                <button
                                onClick={this.setActiveEmailEditFalse}
                                className="badge badge-warning"
                                >
                                Edit
                                </button>
                                </p>) : (
                                <Form
                                    onSubmit={this.editEmail}
                                    ref={(c) => {
                                        this.form = c;
                                    }}
                                >
                                    <div className="form-group">
                                        <label htmlFor="email">Email</label>
                                        <Input
                                            type="text"
                                            className="form-control"
                                            name="email"
                                            value={this.state.email}
                                            onChange={this.onChangeEmail}

                                        />
                                    </div>

                                    {/*<div className="form-group">*/}
                                    {/*    <label htmlFor="description">description</label>*/}
                                    {/*    <Input*/}
                                    {/*        type="text"*/}
                                    {/*        className="form-control"*/}
                                    {/*        name="description"*/}
                                    {/*        value={this.state.description}*/}
                                    {/*        onChange={this.onChangeDescription}*/}

                                    {/*    />*/}
                                    {/*</div>*/}

                                    <div className="form-group">
                                        <button
                                            className="btn btn-outline-secondary btn-group-toggle"
                                            disabled={this.state.loading}
                                        >
                                            {this.state.loading && (
                                                <span className="spinner-border spinner-border-sm"></span>
                                            )}
                                            <span>Update Email</span>
                                        </button>
                                    </div>
                                    {message && (
                                        <div className="form-group">
                                            <div className="alert alert-danger" role="alert">
                                                {message}
                                            </div>
                                        </div>
                                    )}
                                    <CheckButton
                                        style={{ display: "none" }}
                                        ref={(c) => {
                                            this.checkBtn = c;
                                        }}
                                    />
                                </Form> ) }
                        </div>
                        { emailEdit ? (
                            <h4></h4>
                        ) : (
                            <button
                                className="m-3 btn btn-sm btn-danger"
                                onClick={this.setActiveEmailEdit}
                            >
                                Abort
                            </button>)
                        }
                    </p>
                    <p>
                        <strong>Name:</strong> {currentUser.name}
                        <Link
                            to={"/blank/"}
                            className="badge badge-warning"
                        >
                            Edit
                        </Link>
                    </p>
                    <p>
                        <strong>Surname:</strong> {currentUser.surname}
                        <Link
                            to={"/blank/"}
                            className="badge badge-warning"
                        >
                            Edit
                        </Link>
                    </p>
                    <p>
                        <strong>City:</strong> {currentUser.city}
                        <Link
                            to={"/blank/"}
                            className="badge badge-warning"
                        >
                            Edit
                        </Link>
                    </p>

                    <p>
                        <strong>Birth Date:</strong> {currentUser.birthdate ? new Date(currentUser.birthdate._isoformat).toLocaleDateString() : ""}
                        <Link
                            to={"/blank/"}
                            className="badge badge-warning"
                        >
                            Edit
                        </Link>
                    </p>

                    <p>
                        <strong>Profile Picture:</strong> {currentUser.pplink}
                    </p>

                    <p>
                        <strong>Token:</strong> {currentToken}

                    </p>
                    <div className="col-md-6">
                        { communityCreationReq ?(<h4></h4>) : (
                            <Form
                                onSubmit={this.createCommunity}
                                ref={(c) => {
                                    this.form = c;
                                }}
                            >
                                <div className="form-group">
                                    <label htmlFor="communityTitle">communityTitle</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="communityTitle"
                                        value={this.state.communityTitle}
                                        onChange={this.onChangeCommunityTitle}

                                    />
                                </div>

                                <div className="form-group">
                                    <label htmlFor="description">description</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="description"
                                        value={this.state.description}
                                        onChange={this.onChangeDescription}

                                    />
                                </div>

                                <div className="form-group">
                                    <button
                                        className="btn btn-primary btn-block"
                                        disabled={this.state.loading}
                                    >
                                        {this.state.loading && (
                                            <span className="spinner-border spinner-border-sm"></span>
                                        )}
                                        <span>Create Community</span>
                                    </button>
                                </div>
                                {message && (
                                    <div className="form-group">
                                        <div className="alert alert-danger" role="alert">
                                            {message}
                                        </div>
                                    </div>
                                )}
                                <CheckButton
                                    style={{ display: "none" }}
                                    ref={(c) => {
                                        this.checkBtn = c;
                                    }}
                                />
                            </Form> ) }
                    </div>
                    { communityCreationReq ? (
                        <button
                            className="badge badge-dark"
                            onClick={this.setActiveCreationReq}
                        >
                            Create a new community
                        </button>
                    ) : (
                        <button
                            className="m-3 btn btn-sm btn-danger"
                            onClick={this.setActiveCreationReqNonActive}
                        >
                             Abort
                        </button>)
                    }










                </div>
                <Footer/>
            </>
        );
    }
}

function mapStateToProps(state) {
    const { user, usertoken } = state.reg;
    return {
        user, usertoken
    };
}

export default connect(mapStateToProps)(Profile);
