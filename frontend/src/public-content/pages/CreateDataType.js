import React, {Component} from "react";
import Navbar from "../Components/navbar";
import {withRouter} from "react-router-dom";
import DataTypeForm from "../Components/DataTypeForm"
import CreateDataTypeForm from "../Components/CreateDataTypeForm";

class CreateDataType extends Component {
    constructor(props) {
        super(props);
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

    render() {
        const {communityId} = this.state
        return (<>
            <Navbar/>
            <CreateDataTypeForm communityId={communityId}/>
        </>)
    }
}


export default withRouter(CreateDataType)
