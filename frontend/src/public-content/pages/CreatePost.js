import React, {Component} from "react";
import Navbar from "../Components/navbar";
import {useHistory, withRouter} from "react-router-dom";
import UserCommunityService from "../../services/user-community.service";
import CreatePostForm from "../Components/CreatePostForm";
import Profilebar from "../../components/profilebar";

class CreatePost extends Component {

    constructor(props) {
        super(props);
        this.goBack = this.goBack.bind(this);
        this.state = {
            communityId: '',
            dataTypes: []
        }
    }

    goBack(){

        this.props.history.go(-1)
    }

    componentDidMount() {
        this.setState({
            communityId :this.props.match.params.id
        })
        this.retrieveCommunityDataTypes(this.props.match.params.id);
    }
    retrieveCommunityDataTypes(id) {
        UserCommunityService.getCommunityById(id)
            .then(response => {
                const data = response.data['@return'];
                this.setState({
                    dataTypes:data.dataTypes,
                })
            })
            .catch(error => {
                console.log(error.toString());
            });
    }


    render() {
        const {communityId, dataTypes} = this.state
        return (<>
            <Profilebar/>
            <button
            onClick={this.goBack}>
                🔙
            </button>
            <CreatePostForm communityId={communityId} dataTypes={dataTypes} />
        </>)
    }
}


export default withRouter(CreatePost)
