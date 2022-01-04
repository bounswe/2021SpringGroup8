import React, {Component} from "react";
import ProfileService from '../../services/profile.service';
import Map from "../Components/map";
import Container from "@material-ui/core/Container";
import Profilebar from "../../components/profilebar";
import {OutlinedInput} from '@mui/material';
import {makeStyles} from "@material-ui/core/styles";
import {Grid} from "@material-ui/core";

const useStyles = makeStyles({
    customInputLabel: {
        "& legend": {
            visibility: "visible"
        }
    }
});
export default class ProfileView extends Component {
    constructor(props) {
        super(props);
        this.goBack = this.goBack.bind(this);
        this.state = {
            id: '',
            username: '',
        }

    }

    componentDidMount() {
        this.retrievePost(this.props.match.params.id);
    }

    retrievePost() {
        console.log(this.props.match.params.id)
        ProfileService.getProfilePreview(this.props.match.params.id).then(response => {
            console.log(response)
            if (response.data.success !== 'False') {
                const data = response.data['@return'];
                console.log("id")
                console.log(data.id)
                console.log("username")
                console.log(data.username)
                this.setState({
                    id: data.id,
                   username:data.username,
                })
            }else{
                console.log("error")
            }
        }).catch(error => {
            console.log(error)
        })
    }

    goBack(){

        this.props.history.go(-1)
    }

    render() {


        const {username, id} = this.state
        return (

            <div>
                <Profilebar/>
                <button
                    onClick={this.goBack}>
                    🔙
                </button>
                <Container maxWidth="md" style={{alignContent: "center"}}>
                    <div className="container">
                        <header className="jumbotron">
                            <h3>
                                <strong>{username}</strong>
                            </h3>
                        </header>
                        </div>
                    <h1>
                       This is {username}'s profile
                    </h1>
                    {/*<h3>*/}
                    {/*    {id}*/}
                    {/*</h3>*/}
                </Container>
            </div>


        );
    }
}
