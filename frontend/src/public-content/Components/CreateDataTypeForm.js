import React, {useState} from "react";
import Container from '@material-ui/core/Container';
import TextField from '@material-ui/core/TextField'
import {makeStyles} from "@material-ui/core/styles";
import Button from "@material-ui/core/Button"
import IconButton from "@material-ui/core/IconButton";
import RemoveIcon from "@material-ui/icons/Remove"
import AddIcon from "@material-ui/icons/Add"
import {InputLabel, MenuItem, Select} from "@material-ui/core";
import {useHistory} from "react-router-dom";
import userCommunityService from "../../services/user-community.service";

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            align: "center"
        },
        button: {
            margin: theme.spacing(1),

        },
    },
    fieldGroup: {
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "left",
        alignItems: "center",
        margin: theme.spacing(1)
    },
    selectLabel: {
        margin: 0,
    }

}))

export default function CreateDataTypeFrom(props) {
    const {communityId} = props;
    const history = useHistory();

    const classes = useStyles()
    const [inputFields, setInputFields] = useState([
        {
            fieldName: '',
            fieldType: '',
        },
    ]);
    const [typeName, setTypeName] = useState("");

    function handleChangeInput(index, event) {
        const values = [...inputFields];
        values[index][event.target.name] = event.target.value;
        setInputFields(values);
        return undefined;
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        let json_object = {}
        inputFields.map((inputGroup) => {
            json_object[inputGroup["fieldName"]] = inputGroup["fieldType"]
        });
        console.log(communityId, json_object, typeName)
        userCommunityService.createDataTypeForCommunity(communityId, json_object, typeName).then(response => {
            console.log(response)
            if (response.data['@success'] === "True") {
                alert(JSON.stringify("successfully created data type", null, 2));
                history.push('/community/' + communityId);
            }else {
                alert(JSON.stringify(response.data['@error'].toString(), null, 2));
            }

        }).catch(error => {
            console.log(error)
        })

        history.push('/community/' + communityId);

    };

    const handleAddFields = () => {
        setInputFields([...inputFields, {fieldName: '', fieldType: '',}])
    };

    const handleRemoveFields = (index) => {
        const values = [...inputFields];
        values.splice(index, 1);
        setInputFields(values);
    };

    function handleChangeTypeName(event) {
        setTypeName(event.target.value);
        return undefined;
    }

    return (
        <Container maxWidth={"xl"}>
            <h3 align={"center"}> Create New Data Type</h3>
            <form className={classes.root} onSubmit={handleSubmit}>
                <div className={classes.fieldGroup}>
                    <TextField
                        name="typeName"
                        label="Data Type Name"
                        variant="outlined"
                        value={typeName}
                        onChange={event => handleChangeTypeName(event)}
                    />
                </div>
                {inputFields.map((inputField, index) => (
                    <div key={index} className={classes.fieldGroup}>
                        <TextField
                            data-testid={index +'#fieldName'}
                            name="fieldName"
                            label="Field Name"
                            variant="outlined"
                            value={inputField.fieldName}
                            onChange={event => handleChangeInput(index, event)}
                        />
                        <row>
                            <InputLabel id="type-select" className={classes.selectLabel}> Type </InputLabel>
                            <Select
                                name="fieldType"
                                id="type-select"
                                value={inputField.fieldType}
                                label="Field Type"
                                onChange={event => handleChangeInput(index, event)}
                            >
                                <MenuItem value={"str"}>String</MenuItem>
                                <MenuItem value={"int"}>Integer</MenuItem>
                                <MenuItem value={"bool"}>Boolean</MenuItem>
                                <MenuItem value={"location"}>Location</MenuItem>
                            </Select>

                        </row>
                        <IconButton data-testid={index+'#removeFieldGroup'} onClick={() => handleRemoveFields(index)}>
                            <RemoveIcon/>
                        </IconButton>

                        <IconButton data-testid={index+'#addFieldGroup'} onClick={() => handleAddFields()}>
                            <AddIcon/>
                        </IconButton>
                    </div>
                ))}
                <Button variant="contained" color="primary" className={classes.button}
                        onClick={handleSubmit}>Create</Button>
            </form>
        </Container>
    );
}