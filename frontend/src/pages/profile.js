import React, {Component} from "react";
import {Redirect} from 'react-router-dom';
import {connect} from "react-redux";
import Profilebar from "../components/profilebar";

import Footer from "../public-content/Components/footer/footer";
import UserCommunityService from "../services/user-community.service";
import ProfileService from "../services/profile.service";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import {Link} from "react-router-dom";
import "../App.css";


class Profile extends Component {

    constructor(props) {
        super(props);
        this.createCommunity = this.createCommunity.bind(this);
        this.editEmail = this.editEmail.bind(this);
        this.editName = this.editName.bind(this);
        this.editSurname = this.editSurname.bind(this);
        this.editCity = this.editCity.bind(this);
        this.editBirthdate = this.editBirthdate.bind(this);
        this.onChangeCommunityTitle = this.onChangeCommunityTitle.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeSurname = this.onChangeSurname.bind(this);
        this.onChangeCity = this.onChangeCity.bind(this);
        this.onChangeBirthdate = this.onChangeBirthdate.bind(this);
        this.onChangeDescription = this.onChangeDescription.bind(this);
        this.setActiveCreationReq = this.setActiveCreationReq.bind(this);
        this.setActiveCreationReqNonActive = this.setActiveCreationReqNonActive.bind(this);
        this.setActiveEmailEdit = this.setActiveEmailEdit.bind(this);
        this.setActiveEmailEditFalse = this.setActiveEmailEditFalse.bind(this);
        this.setActiveNameEdit = this.setActiveNameEdit.bind(this);
        this.setActiveNameEditFalse = this.setActiveNameEditFalse.bind(this);
        this.setActiveSurnameEdit = this.setActiveSurnameEdit.bind(this);
        this.setActiveSurnameEditFalse = this.setActiveSurnameEditFalse.bind(this);
        this.setActiveCityEdit = this.setActiveCityEdit.bind(this);
        this.setActiveCityEditFalse = this.setActiveCityEditFalse.bind(this);
        this.setActiveBirthdateEdit = this.setActiveBirthdateEdit.bind(this);
        this.setActiveBirthdateEditFalse = this.setActiveBirthdateEditFalse.bind(this);
        this.setSeeSubsCommsActive = this.setSeeSubsCommsActive.bind(this);
        this.setSeeSubsCommsActiveFalse = this.setSeeSubsCommsActiveFalse.bind(this);
        this.unsubToComm = this.unsubToComm.bind(this);

        this.deleteComm = this.deleteComm.bind(this);
        this.setSeeSubsCommsActive2 = this.setSeeSubsCommsActive2.bind(this);
        this.setSeeSubsCommsActiveFalse2 = this.setSeeSubsCommsActiveFalse2.bind(this);
        this.state = {
            communityTitle: "",
            description: "",
            loading: false,
            communityCreationReq: true,
            emailEdit: true,
            nameEdit: true,
            surnameEdit: true,
            cityEdit: true,
            birthdateEdit: true,
            email: "",
            name: "",
            surname: "",
            city: "",
            birthdate: "",
            seeSubsComms: true,
            seeSubsComms2: true,
            currCommId: null,
        };
    }

    unsubToComm(id) {
        console.log("id")
        console.log(id)
        console.log("this.state")
        console.log(this.state)
        console.log("this.props.match.params")
        console.log(this.props.match.params)
        UserCommunityService.unsubscribeCommunity(
            id
        )
            .then(
                response => {
                    if (response.data['@success'] !== 'False') {
                        //console.log(response.data['@return']);
                        console.log("deleted")


                        window.location.reload(false);
                        //history.push('/communities');
                    } else {
                        console.log(response.data['@error']);
                        alert(response.data['@error']);
                    }
                })
            .catch();

    }

    deleteComm(id) {
        console.log("Entered into delete community ")
        console.log("id");
        console.log(id);
        UserCommunityService
            .deleteCommunity(
                id
            )
            .then(
                response => {
                    if (response.data['@success'] !== 'False') {
                        //console.log(response.data['@return']);
                        console.log("deleted")


                        window.location.reload(false);
                        //history.push('/communities');
                    } else {
                        console.log(response.data['@error']);
                        alert(response.data['@error']);
                    }
                })
            .catch();
    }


    setSeeSubsCommsActive() {
        this.setState({
            seeSubsComms: true,
        });
        window.location.reload(false);

    }

    setSeeSubsCommsActiveFalse() {

        ProfileService.getMyProfile().then(
            response => {
                if (response.data['@success'] !== 'False') {
                    console.log(response.data['@return']);

                    localStorage.removeItem("user");
                    localStorage.setItem("user", JSON.stringify(response.data['@return']));

                    this.setState({
                        seeSubsComms: false,
                    });
                } else {
                    console.log(response.data['@error']);
                    alert(response.data['@error']);
                }
            })
            .catch();

    }

    setSeeSubsCommsActive2() {
        this.setState({
            seeSubsComms2: true,

        });
        window.location.reload(false);

    }

    setSeeSubsCommsActiveFalse2() {

        ProfileService.getMyProfile().then(
            response => {
                if (response.data['@success'] !== 'False') {
                    console.log(response.data['@return']);

                    localStorage.removeItem("user");
                    localStorage.setItem("user", JSON.stringify(response.data['@return']));

                    this.setState({
                        seeSubsComms2: false,
                    });
                } else {
                    console.log(response.data['@error']);
                    alert(response.data['@error']);
                }
            })
            .catch();

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

    setActiveNameEdit() {
        this.setState({
            nameEdit: true,

        });
    }

    setActiveNameEditFalse() {
        this.setState({
            nameEdit: false,

        });
    }

    setActiveSurnameEditFalse() {
        this.setState({
            surnameEdit: false,

        });
    }

    setActiveSurnameEdit() {
        this.setState({
            surnameEdit: true,

        });
    }

    setActiveCityEditFalse() {
        this.setState({
            cityEdit: false,

        });
    }

    setActiveCityEdit() {
        this.setState({
            cityEdit: true,

        });
    }

    setActiveBirthdateEditFalse() {
        this.setState({
            birthdateEdit: false,

        });
    }

    setActiveBirthdateEdit() {
        this.setState({
            birthdateEdit: true,

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

    onChangeName(e) {
        this.setState({
            name: e.target.value,
        });
    }

    onChangeSurname(e) {
        this.setState({
            surname: e.target.value,
        });
    }

    onChangeCity(e) {
        this.setState({
            city: e.target.value,
        });
    }

    onChangeBirthdate(e) {
        this.setState({
            birthdate: e.target.value,
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


        const {history} = this.props;


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
                        this.setState({
                            loading: false,
                        });
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

    }

    editEmail(e) {
        e.preventDefault();

        this.setState({
            loading: true,
        });

        this.form.validateAll();

        if (this.checkBtn.context._errors.length === 0) {
            ProfileService
                .updateEmail(
                    this.state.email,
                )
                .then(
                    response => {
                        if (response.data['@success'] !== 'False') {
                            localStorage.removeItem("user");
                            localStorage.setItem("user", JSON.stringify(response.data['@return']));
                            this.setActiveEmailEdit();
                            window.location.reload(false);
                        } else {
                            console.log(response.data['@error']);
                            alert(response.data['@error']);
                        }
                    })
                .catch();
        } else {
            this.setState({
                loading: false,
            });
        }
    }

    editName(e) {
        e.preventDefault();

        this.setState({
            loading: true,
        });
        ProfileService
            .updateName(
                this.state.name,
            )
            .then(
                response => {
                    if (response.data['@success'] !== 'False') {
                        localStorage.removeItem("user");
                        localStorage.setItem("user", JSON.stringify(response.data['@return']));

                        window.location.reload(false);
                    } else {
                        console.log(response.data['@error']);
                        this.setState({
                            loading: false,
                        });
                        alert(response.data['@error']);
                    }
                })
            .catch();

    }

    editSurname(e) {
        e.preventDefault();
        this.setState({
            loading: true,
        });
        ProfileService
            .updateSurname(
                this.state.surname,
            )
            .then(
                response => {
                    if (response.data['@success'] !== 'False') {
                        localStorage.removeItem("user");
                        localStorage.setItem("user", JSON.stringify(response.data['@return']));
                        window.location.reload(false);
                    } else {
                        this.setState({
                            loading: false,
                        });
                        console.log(response.data['@error']);
                        alert(response.data['@error']);
                    }
                });


    }

    editCity(e) {
        e.preventDefault();
        this.setState({
            loading: true,
        });
        ProfileService
            .updateSurname(
                this.state.surname,
            )
            .then(
                response => {
                    if (response.data['@success'] !== 'False') {
                        localStorage.removeItem("user");
                        localStorage.setItem("user", JSON.stringify(response.data['@return']));
                        window.location.reload(false);
                    } else {
                        this.setState({
                            loading: false,
                        });
                        console.log(response.data['@error']);
                        alert(response.data['@error']);
                    }
                });


    }

    editBirthdate(e) {
        e.preventDefault();
        this.setState({
            loading: true,
        });
        this.form.validateAll();
        if (this.checkBtn.context._errors.length === 0) {
            ProfileService
                .updateSurname(
                    this.state.surname,
                )
                .then(
                    response => {
                        if (response.data['@success'] !== 'False') {
                            localStorage.removeItem("user");
                            localStorage.setItem("user", JSON.stringify(response.data['@return']));
                            window.location.reload(false);
                        } else {
                            console.log(response.data['@error']);
                            alert(response.data['@error']);
                        }
                    });
        } else {
            this.setState({
                loading: false,
            });
        }
    }


    render() {
        const {
            communityCreationReq: communityCreationReq, emailEdit: emailEdit, seeSubsComms: seeSubsComms,
            seeSubsComms2: seeSubsComms2, nameEdit: nameEdit, surnameEdit: surnameEdit,
            cityEdit: cityEdit, birthdateEdit: birthdateEdit
        } = this.state;
        const {user: currentUser} = this.props;

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
                    {/*<p>*/}
                    {/*    <strong>Id:</strong> {currentUser.id}*/}
                    {/*</p>*/}
                    <p>
                        {emailEdit ? (
                            <p>
                                <strong>Email:</strong> {currentUser.email}
                                <button
                                    onClick={this.setActiveEmailEditFalse}
                                    className="badge badge-warning"
                                >
                                    Edit
                                </button>
                            </p>) : (
                            <p>
                                <div className="col-md-4">
                                    <Form
                                        onSubmit={this.editEmail}
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
                                        <button
                                            className="m-3 btn btn-sm btn-danger"
                                            onClick={this.setActiveEmailEdit}
                                        >
                                            Abort
                                        </button>
                                    </Form></div>
                            </p>)}
                    </p>
                    <p>
                        {nameEdit ? (
                            <p>
                                <strong>Name:</strong> {currentUser.name}
                                <button
                                    onClick={this.setActiveNameEditFalse}
                                    className="badge badge-warning"
                                >
                                    Edit
                                </button>
                            </p>) : (
                            <p>
                                <div className="col-md-4">
                                    <Form
                                        onSubmit={this.editName}
                                    >
                                        <div className="form-group">
                                            <label htmlFor="name">Name</label>
                                            <Input
                                                type="text"
                                                className="form-control"
                                                name="name"
                                                value={this.state.name}
                                                onChange={this.onChangeName}/>
                                        </div>
                                        <div className="form-group">
                                            <button
                                                className="btn btn-outline-secondary btn-group-toggle"
                                                disabled={this.state.loading}
                                            >
                                                {this.state.loading && (
                                                    <span className="spinner-border spinner-border-sm"></span>
                                                )}
                                                <span>Update Name</span>
                                            </button>
                                        </div>
                                        <button
                                            className="m-3 btn btn-sm btn-danger"
                                            onClick={this.setActiveNameEdit}
                                        >
                                            Abort
                                        </button>
                                    </Form></div>
                            </p>)}
                        {/*<strong>Name:</strong> {currentUser.name}*/}
                        {/*<Link*/}
                        {/*    to={"/blank/"}*/}
                        {/*    className="badge badge-warning"*/}
                        {/*>*/}
                        {/*    Edit*/}
                        {/*</Link>*/}

                    </p>
                    <p>

                        {surnameEdit ? (
                            <p>
                                <strong>Surname:</strong> {currentUser.surname}
                                <button
                                    onClick={this.setActiveSurnameEditFalse}
                                    className="badge badge-warning"
                                >
                                    Edit
                                </button>
                            </p>) : (
                            <p>
                                <div className="col-md-4">
                                    <Form
                                        onSubmit={this.editSurname}
                                    >
                                        <div className="form-group">
                                            <label htmlFor="surname">Surname</label>
                                            <Input
                                                type="text"
                                                className="form-control"
                                                name="surname"
                                                value={this.state.surname}
                                                onChange={this.onChangeSurname}/>
                                        </div>
                                        <div className="form-group">
                                            <button
                                                className="btn btn-outline-secondary btn-group-toggle"
                                                disabled={this.state.loading}
                                            >
                                                {this.state.loading && (
                                                    <span className="spinner-border spinner-border-sm"></span>
                                                )}
                                                <span>Update Surname</span>
                                            </button>
                                        </div>
                                        <button
                                            className="m-3 btn btn-sm btn-danger"
                                            onClick={this.setActiveSurnameEdit}
                                        >
                                            Abort
                                        </button>
                                    </Form></div>
                            </p>)}


                    </p>
                    <p>

                        {cityEdit ? (
                            <p>
                                <strong>City:</strong> {currentUser.city}
                                <button
                                    onClick={this.setActiveCityEditFalse}
                                    className="badge badge-warning"
                                >
                                    Edit
                                </button>
                            </p>) : (
                            <p>
                                <div className="col-md-4">
                                    <Form
                                        onSubmit={this.editCity}
                                    >
                                        <div className="form-group">
                                            <label htmlFor="name">City</label>
                                            <Input
                                                type="text"
                                                className="form-control"
                                                name="name"
                                                value={this.state.city}
                                                onChange={this.onChangeCity}/>
                                        </div>
                                        <div className="form-group">
                                            <button
                                                className="btn btn-outline-secondary btn-group-toggle"
                                                disabled={this.state.loading}
                                            >
                                                {this.state.loading && (
                                                    <span className="spinner-border spinner-border-sm"></span>
                                                )}
                                                <span>Update City</span>
                                            </button>
                                        </div>
                                        <button
                                            className="m-3 btn btn-sm btn-danger"
                                            onClick={this.setActiveCityEdit}
                                        >
                                            Abort
                                        </button>
                                    </Form></div>
                            </p>)}


                    </p>

                    <p>

                        {birthdateEdit ? (
                            <p>
                                <strong>Birthdate:</strong> {currentUser.birthdate ? new Date(currentUser.birthdate._isoformat).toLocaleDateString() : ""}
                                <button
                                    onClick={this.setActiveBirthdateEditFalse}
                                    className="badge badge-warning"
                                >
                                    Edit
                                </button>
                            </p>) : (
                            <p>
                                <div className="col-md-4">
                                    <Form
                                        onSubmit={this.editName}
                                    >
                                        <div className="form-group">
                                            <label htmlFor="birthdate">Birthdate</label>
                                            <Input
                                                type="text"
                                                className="form-control"
                                                name="birthdate"
                                                value={this.state.birthdate}
                                                onChange={this.onChangeBirthdate}/>
                                        </div>
                                        <div className="form-group">
                                            <button
                                                className="btn btn-outline-secondary btn-group-toggle"
                                                disabled={this.state.loading}
                                            >
                                                {this.state.loading && (
                                                    <span className="spinner-border spinner-border-sm"></span>
                                                )}
                                                <span>Update Birthdate</span>
                                            </button>
                                        </div>
                                        <button
                                            className="m-3 btn btn-sm btn-danger"
                                            onClick={this.setActiveBirthdateEdit}
                                        >
                                            Abort
                                        </button>
                                    </Form></div>
                            </p>)}


                    </p>
                    {seeSubsComms ? (
                        <p>
                            <strong>Subscribed Communities :</strong>
                            <button
                                onClick={this.setSeeSubsCommsActiveFalse}
                                className="badge badge-pill"
                            >
                                Expand
                            </button>
                        </p>) : (
                        <>
                            <strong>Subscribed Communities :</strong>
                            <button
                                onClick={this.setSeeSubsCommsActive}
                                className="badge badge-secondary"
                            >
                                Collapse
                            </button>
                            <button
                                onClick={this.setSeeSubsCommsActive2}
                                className="badge badge-light"
                            >
                                🔄
                            </button>
                            {currentUser.subscribes.map(function (subcom) {
                                return <div className="list row">
                                    <div className="col-md-12">
                                        <div className="input-group mb-1">
                                            <div className="input-group-append">

                                                <Link
                                                    to={"/community/" + subcom.id}
                                                    className="badge badge-info mb-1 col-md-12"
                                                >
                                                    <div className="my-cls"><h5 className="my-cls">
                                                        <strong>{subcom.CommunityTitle}</strong></h5></div>
                                                    <h6 className="my-cls">{subcom.description}</h6>

                                                </Link>
                                                <button
                                                    onClick={() => this.unsubToComm(subcom.id)}
                                                    className="badge badge-warning"
                                                >
                                                    Unsubscribe
                                                </button>

                                            </div>
                                        </div>
                                    </div>
                                </div>;

                            }, this)
                            }
                        </>
                    )}


                    {seeSubsComms2 ? (
                        <p>
                            <strong>My Communities :</strong>
                            <button
                                onClick={this.setSeeSubsCommsActiveFalse2}
                                className="badge badge-pill"
                            >
                                Expand
                            </button>
                        </p>) : (
                        <>
                            <strong> My Communities :</strong>
                            <button
                                onClick={this.setSeeSubsCommsActive2}
                                className="badge badge-secondary"
                            >
                                Collapse
                            </button>
                            <button
                                onClick={this.setSeeSubsCommsActive2}
                                className="badge badge-light"
                            >
                                🔄
                            </button>
                            {currentUser.createdCommunities.map(function (subcom) {
                                return <div className="list row">
                                    <div className="col-md-12">
                                        <div className="input-group mb-1">
                                            <div className="input-group-append">

                                                <Link
                                                    to={"/community/" + subcom.id}
                                                    className="badge badge-info mb-1 col-md-12"
                                                >
                                                    <div className="my-cls"><h5 className="my-cls">
                                                        <strong>{subcom.CommunityTitle}</strong></h5></div>
                                                    <h6 className="my-cls">{subcom.description}</h6>

                                                </Link>
                                                <button
                                                    onClick={() => this.deleteComm(subcom.id)}
                                                    className="badge badge-danger"
                                                >
                                                    Delete
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>;

                            }, this)
                            }
                        </>
                    )}


                    {/*{ seeMyComms ? (*/}
                    {/*    <p>*/}
                    {/*        <strong>My Communities :</strong>*/}
                    {/*        <button*/}
                    {/*            onClick={this.setSeeMyCommsActiveFalse}*/}
                    {/*            className="badge badge-success"*/}
                    {/*        >*/}
                    {/*            Expand*/}
                    {/*        </button>*/}
                    {/*    </p>) : (*/}
                    {/*    <>*/}
                    {/*        <strong>My Communities :</strong>*/}
                    {/*        <button*/}
                    {/*            onClick={this.setSeeMyCommsActive}*/}
                    {/*            className="badge badge-secondary"*/}
                    {/*        >*/}
                    {/*            Collapse*/}
                    {/*        </button>*/}
                    {/*        {currentUser.createdCommunities.map(function (subcom) {*/}
                    {/*            return  <div className="list row">*/}
                    {/*                <div className="col-md-12">*/}
                    {/*                    <div className="input-group mb-1">*/}
                    {/*                        <div className="input-group-append">*/}
                    {/*                            <Link*/}
                    {/*                                to={"/community/"+subcom.id}*/}
                    {/*                                className="badge badge-info mb-1 col-md-12"*/}
                    {/*                            ><div className="my-cls2"> <h5><strong>{subcom.CommunityTitle}</strong></h5> </div>*/}
                    {/*                                <h6>{ subcom.description}</h6>*/}
                    {/*                            </Link>*/}
                    {/*                            <button*/}
                    {/*                                onClick={() => this.deleteComm(subcom.id)}*/}
                    {/*                                //onClick={this.unsubToComm}*/}
                    {/*                                className="badge badge-danger"*/}
                    {/*                            >*/}
                    {/*                                Delete*/}
                    {/*                            </button>*/}
                    {/*                        </div>*/}
                    {/*                    </div>*/}
                    {/*                </div>*/}
                    {/*            </div>;*/}
                    {/*        }, this)*/}
                    {/*        }*/}
                    {/*    </>*/}
                    {/*) }*/}


                    {/*<p>*/}
                    {/*    <strong>Token:</strong> {currentToken}*/}
                    {/*</p>*/}
                    <div className="col-md-6">
                        {communityCreationReq ? (<h4></h4>) : (
                            <Form
                                onSubmit={this.createCommunity}
                            >
                                <div className="form-group">
                                    <label htmlFor="communityTitle">communityTitle</label>
                                    <Input
                                        type="text"
                                        className="form-control"
                                        name="communityTitle"
                                        value={this.state.CommunityTitle}
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
                                        className="btn btn-info btn-block"
                                        disabled={this.state.loading}
                                    >
                                        {this.state.loading && (
                                            <span className="spinner-border spinner-border-sm"></span>
                                        )}
                                        <span>Create Community</span>
                                    </button>
                                </div>
                            </Form>)}
                    </div>
                    {communityCreationReq ? (
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
    const {user, usertoken} = state.reg;
    return {
        user, usertoken
    };
}

export default connect(mapStateToProps)(Profile);
