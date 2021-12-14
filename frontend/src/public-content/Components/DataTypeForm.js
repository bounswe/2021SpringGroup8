import React from 'react';
import {useFormik, Field, Form, Formik, FormikProps, FormikProvider} from 'formik';
import userCommunityService from "../../services/user-community.service";
import {useHistory} from "react-router-dom";

export default function DataTypeForm(props) {
    const {communityId} = props;
    const history = useHistory()
    const formik = useFormik({
        initialValues: {
            fieldName1: '',
            fieldType1: 'str',
            fieldName2: '',
            fieldType2: '',
            fieldName3: '',
            fieldType3: '',
            name: '',
        },
        onSubmit: (values,actions) => {
            const name =  values.name;
            const fieldName1 =  values.fieldName1;
            const fieldType1 =  values.fieldType1;
            const fieldName2 =  values.fieldName2;
            const fieldType2 =  values.fieldType2;
            const fieldName3 =  values.fieldName3;
            const fieldType3 =  values.fieldType3;
            const json_object = {
            }
            json_object[fieldName1] = fieldType1;
            json_object[fieldName2] = fieldType2;
            json_object[fieldName3] = fieldType3;
            console.log(json_object)
            userCommunityService.createDataTypeForCommunity(communityId, json_object, name).then(response => {
                console.log(response)
                if(response.data['@success'] === "True"){
                    alert(JSON.stringify({"message": "successfully created data type"}, null, 2));
                    history.push('/community/' + communityId);
                }
                actions.setSubmitting(false);
                alert(JSON.stringify({"message": response.data['@error'] }, null, 2));
            }).catch(error => {
                console.log(error)
            })

            history.push('/community/' + communityId);

        },
    });
    return (
        <FormikProvider value={formik}>
            <form onSubmit={formik.handleSubmit}>
                <label htmlFor="name">Data Type Name </label>
                <input
                    id="name"
                    name="name"
                    type="text"
                    onChange={formik.handleChange}
                    value={formik.values.name}
                />
                <label htmlFor="fieldName1">Field Name 1</label>
                <input
                    id="fieldName1"
                    name="fieldName1"
                    type="text"
                    onChange={formik.handleChange}
                    value={formik.values.fieldName1}
                />

                <label htmlFor="fieldType1">Field Type 1</label>
                <Field as="select" name="fieldType1" class>
                    <option value="str">String</option>
                    <option value="int">Integer</option>
                    <option value="location">Location</option>
                </Field>

                <label htmlFor="fieldName2">Field Name 2</label>
                <input
                    id="fieldName2"
                    name="fieldName2"
                    type="text"
                    onChange={formik.handleChange}
                    value={formik.values.fieldName2}
                />

                <label htmlFor="fieldType2">Field Type 2</label>
                <Field as="select" name="fieldType2" class>
                    <option value="str">String</option>
                    <option value="int">Integer</option>
                    <option value="location">Location</option>
                </Field>

                <label htmlFor="fieldName3">Field Name 3</label>
                <input
                    id="fieldName3"
                    name="fieldName3"
                    type="text"
                    onChange={formik.handleChange}
                    value={formik.values.fieldName3}
                />

                <label htmlFor="fieldType3">Field Type 3</label>
                <Field as="select" name="fieldType3" class>
                    <option value="str">String</option>
                    <option value="int">Integer</option>
                    <option value="location">Location</option>
                </Field>



                <button type="submit">Submit</button>
            </form>
        </FormikProvider>

    );
};
