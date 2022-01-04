import React, {Component} from "react";
import PostService from '../../services/post.service';
import Map from "../Components/map";
import Container from "@material-ui/core/Container";
import Profilebar from "../../components/profilebar";
import {Grid} from "@material-ui/core";

export default class PostView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            postTitle: '',
            id: '',
            postedByID: '',
            postedByName: '',
            postObject: {},
            dataTypeName: '',
        }

    }

    componentDidMount() {
        this.retrievePost(this.props.match.params.id);
    }

    retrievePost(id) {
        PostService.getPost(id).then(response => {
            console.log(response)
            if (response.data.success === 'True') {
                const data = response.data['@return'];
                this.setState({
                    postTitle: data.postTitle,
                    id: data.id,
                    postObject: data.fieldValues,
                    postedByID: data.postedBy.id,
                    postedByName: data.postedBy.username,
                    dataTypeName: data.dataTypeName
                })
            }
        }).catch(error => {
            console.log(error)
        })
    }

    render() {
        const {postTitle, postedByName, postObject} = this.state
        let rows = [];
        for (let field in postObject) {
            if (!postObject[field]['@type']) {
                rows.push(<column>
                    <h4>{field}</h4>
                    <span style={{fontSize: 18}}>{(postObject[field]).toString()}</span>
                </column>)
                /*rows.push(<h3>{field} :{postObject[field]} </h3>)*/
            } else if (postObject[field]['@type'] === "Location.Object") {
                rows.push(<Grid container spacing={2}>
                    <Grid item xs={4}>
                        <h4>Location Name</h4><span style={{fontSize: 18}}>{postObject[field]["locname"]}</span>
                    </Grid>
                    <Grid item xs={4}>
                        <h4>Location Longitude</h4><span style={{fontSize: 18}}>{postObject[field]["longitude"]}</span>
                    </Grid>
                    <Grid item xs={4}>
                        <h4>Location Latitude</h4><span style={{fontSize: 18}}>{postObject[field]["latitude"]}</span>
                    </Grid>
                </Grid>)
                rows.push(<div><Map marker={{lat: postObject[field]["latitude"], lng: postObject[field]["longitude"]}}/>
                </div>)
            }
        }
        return (
            <div>
                <Profilebar/>
                <Container maxWidth="md" style={{alignContent: "center"}}>

                    <h1>
                        {postTitle}
                    </h1>
                    <h3>
                        Posted By: {postedByName}
                    </h3>
                    {rows}

                </Container>
            </div>


        );
    }
}
