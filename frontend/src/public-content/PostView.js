import React, {Component} from "react";
import PostService from '../services/post.service';

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
        for(let field in postObject) {
            if(field !== 'yer') rows.push(<h3>{field} :{postObject[field]} </h3>)
            else{
                for(let field2 in postObject[field]){
                    console.log(field2)
                    rows.push(<h4>{field2} :{postObject[field][field2]}  </h4>)
                }
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
