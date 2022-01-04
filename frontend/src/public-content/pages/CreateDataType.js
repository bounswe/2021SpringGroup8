import React, {Component} from "react";
import Navbar from "../Components/navbar";
import {withRouter} from "react-router-dom";
import CreateDataTypeForm from "../Components/CreateDataTypeForm";
import Profilebar from "../../components/profilebar";

class CreateDataType extends Component {

    constructor(props) {
        super(props);
        this.goBack = this.goBack.bind(this);
        this.state = {
            communityId: '',
        }

    }

    componentDidMount() {
        this.setState({
            communityId :this.props.match.params.id
        })
        console.log(this.props.match.params.id);
    }

    goBack(){

        this.props.history.go(-1)
    }

    render() {
        const {communityId} = this.state
        return (<>
            <Profilebar/>
            <button
                variant="contained" color="primary"
                onClick={this.goBack}>
                🔙
            </button>
            <CreateDataTypeForm communityId={communityId}/>
        </>)
    }
}


export default withRouter(CreateDataType)
