import React, {Component} from "react";
import PostService from '../../services/post.service';
import Map from "../Components/map";

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
        const {postTitle, id, postedByID, postedByName, postObject} = this.state
        var rows = [];
        for (let field in postObject) {
            if (!postObject[field]['@type']) {
                rows.push(<h3>{field} :{postObject[field]} </h3>)
            } else if (postObject[field]['@type'] === "Location.Object") {
                rows.push(<h4>Location Name :{postObject[field]["locname"]}  </h4>)
                rows.push(<h4>Location Longitude :{postObject[field]["longitude"]}  </h4>)
                rows.push(<h4>Location Latitude :{postObject[field]["latitude"]}  </h4>)
                rows.push(<div><Map marker={{lat: postObject[field]["latitude"], lng: postObject[field]["longitude"]}}/>
                </div>)
            }
        }
        return (
            <div>
                <h1>
                    {postTitle}
                </h1>
                <h2>
                    Posted By: {postedByName}
                </h2>
                {rows}


            </div>


        );
    }
}
