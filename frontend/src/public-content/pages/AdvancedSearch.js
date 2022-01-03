import React, {Component} from "react";

import UserCommunityService from "../../services/user-community.service";
import {withRouter} from "react-router-dom";
import {Container} from "react-bootstrap";
import {Grid} from "@mui/material";
import Header from "../Components/header/header2";
import Profilebar from "../../components/profilebar";
import SearchIcon from "@mui/icons-material/Search";
import IconButton from "@mui/material/IconButton";
import InputBase from "@mui/material/InputBase";
//import AdvancedSearchForm from "../Components/AdvancedSearchForm";
import {InputLabel, MenuItem, Select, Button} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";

export default class AdvancedSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            communities: [],
            communityId: "",
            dataTypes: [],
            dataType: "",
            fields: {},
            fieldValues: [],
            isLoaded: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSearchButton = this.handleSearchButton.bind(this)

    }

    componentDidMount() {
        this.retrieveCommunities();
    }

    retrieveCommunities() {
        UserCommunityService.getCommunities()
            .then((response) => {
                this.setState({
                    communities: response.data["@return"],
                    isLoaded: true,
                });
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e.toString());
            });
    }


    goCommunity(event) {
        console.log(event);
    }

    goProfile(id) {
        console.log(id);
        this.props.history.push("/profile/" + id);
    }

    // text box => onchange = {handleChange}
    handleChange(event) {
        this.setState({searchValue: event.target.value});
    }

    retrieveCommunity(id) {
        UserCommunityService.getCommunityById(id)
            .then(async (response) => {
                this.setState({
                    dataTypes: response.data['@return'].dataTypes,
                })
                console.log(this.state.dataTypes)
            })
            .catch((e) => {
                console.log(e.toString());
            });
    }

    handleChangeCommunity(event) {
        this.setState({
            communityId: event.target.value
        })
        this.retrieveCommunity(event.target.value)
    }

    handleChangeInput(index, event) {
        const values = [...this.state.fieldValues];
        console.log(values)
        values[index][event.target.name] = event.target.value;
        this.setState({fieldValues: values});
    }

    handleChangeDataType(event) {
        const dataTypes = this.state.dataTypes;
        this.setState({
            dataType: event.target.value,
            fields: dataTypes[(event.target.value)].fields,
        })
        let field_array = []

        for (let field in dataTypes[(event.target.value)].fields) {
            if (dataTypes[(event.target.value)].fields[field] === 'location') {
                field_array.push({
                    fieldName: field,
                    fieldType: dataTypes[(event.target.value)].fields[field],
                    locname: "",
                    longitude: "",
                    latitude: "",
                    fieldOperator: ""

                })
            } else {
                field_array.push({
                    fieldName: field,
                    fieldType: dataTypes[(event.target.value)].fields[field],
                    fieldValue: "",
                    fieldOperator: ""
                })
            }

        }
        this.setState({fieldValues: field_array})
    }

    handleSearchButton() {
        //TODO add search service
        console.log(this.state)
    }

    render() {
        const {communities, dataTypes, dataType, fieldValues, isLoaded, communityId} = this.state;
        let communities_render = communities.length > 0
            && communities.map((item, index) => {
                return (
                    <MenuItem key={item.id} value={item.id}>{item.CommunityTitle}</MenuItem>
                )
            }, this);

        let dataTypes_render = dataTypes.length > 0
            && dataTypes.map((item, index) => {
                return (
                    <MenuItem value={index}>{item.name}</MenuItem>
                )
            }, this);
        let fieldValues_render = fieldValues.length > 0
            && fieldValues.map((item, index) => {
                if (item.fieldType === 'str') {
                    return (
                        <Container>
                            <InputLabel id={index.toString()}> {item.fieldName} </InputLabel>
                            <TextField
                                data-testid={index.toString()}
                                name="fieldValue"
                                id={index.toString()}
                                variant="outlined"
                                value={item.fieldValue}
                                onChange={event => this.handleChangeInput(index, event)}
                            />
                        </Container>
                    )
                } else if (item.fieldType === 'int') {
                    return (<Container style={{display: "flex", alignItems: "center"}}>
                        <div>
                            <InputLabel id={index.toString()}> {item.fieldName} </InputLabel>
                            <TextField
                                data-testid={index}
                                name="fieldValue"
                                id={index.toString()}
                                variant="outlined"
                                value={item.fieldValue}
                                type="number"
                                onChange={event => this.handleChangeInput(index, event)}
                            />
                        </div>
                        <div>
                            <InputLabel id="fieldOperator"> Operator </InputLabel>
                            <Select
                                name="fieldOperator"
                                id="fieldOperator"
                                value={item.fieldOperator}
                                label="fieldOperator"
                                defaultValue="equals"
                                onChange={event => this.handleChangeInput(index, event)}
                            >
                                <MenuItem key="greater" value="greater">Greater</MenuItem>
                                <MenuItem key="smaller" value="smaller">Smaller</MenuItem>
                                <MenuItem key="equals" value="equals">Equals</MenuItem>
                            </Select>
                        </div>


                    </Container>)
                } else if (item.fieldType === 'location') {

                }
            }, this);



        return (
            <>
                <Profilebar/>
                <Header/>


                <Container fluid="md">
                    <Container>
                        <InputLabel id="community"> Community </InputLabel>
                        <Select
                            name="community"
                            id="community"
                            value={communityId}
                            label="community"
                            onChange={event => this.handleChangeCommunity(event)}
                        >
                            {communities_render}
                        </Select>

                    </Container>


                    <Container>
                        <InputLabel id="dataType"> Data Type </InputLabel>
                        <Select
                            name="dataType"
                            id="dataType"
                            value={dataType}
                            label="Data"
                            onChange={event => this.handleChangeDataType(event)}
                        >
                            {dataTypes_render}
                        </Select>
                    </Container>


                    {fieldValues_render}

                    <Container>
                        <Button type="submit" variant="contained"
                                onClick={this.handleSearchButton}> Search </Button>
                    </Container>

                </Container>
            </>
        );
    }
}


