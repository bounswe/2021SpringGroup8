import React, {Component} from "react";
import Navbar from "../Components/navbar";
import {withRouter} from "react-router-dom";
import UserCommunityService from "../../services/user-community.service";
import CreatePostForm from "../Components/CreatePostForm";

class CreatePost extends Component {
    constructor(props) {
        super(props);
        this.state = {
            communityId: '',
            dataTypes: []
        }
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
                console.log("data.dataTypes")
                console.log(data.dataTypes)
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
            <Navbar/>
            <CreatePostForm communityId={communityId} dataTypes={dataTypes} />

        </>)
    }
}


export default withRouter(CreatePost)
