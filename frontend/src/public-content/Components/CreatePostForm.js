import React, {useEffect, useState} from 'react';
import {useFormik, Field, Form, Formik, FormikProps, FormikProvider} from 'formik';
import PostService from '../../services/post.service'
export default function CreatePostForm(props) {
    const {communityId, dataTypes} = props;
    const [i, setI] = useState(0);
    let initialValues = {}
    if (dataTypes.length > 0) {
        Object.keys(dataTypes[i].fields).map(field => {
            const field_type = dataTypes[i].fields[field]
            if (field_type === "str" || field_type === "int") {
                initialValues[field] = ''
            } else {
                initialValues['locname'] = ''
                initialValues['longitude'] = ''
                initialValues['latitude'] = ''

            }

        });
    }
    const formik = useFormik({
        initialValues: initialValues,
        onSubmit: (values, actions) => {
            let request_json = {}
            const title = values.title
            if (dataTypes.length > 0) {
                const datatypename = dataTypes[i].name
                Object.keys(dataTypes[i].fields).map(field => {
                    const field_type = dataTypes[i].fields[field]
                    if (field_type === "str" || field_type === "int") {
                        request_json[field] = values[field]
                    } else {
                        request_json[field] = {
                            locname : values.locname,
                            longitude: values.longitude,
                            latitude: values.latitude
                        }
                    }

                });
                console.log(title, datatypename, request_json)
                PostService.submitPost(communityId,title,datatypename,request_json).then( response => {
                    console.log(response)
                }).catch(error => {
                    console.log(error)
                })
            }
            actions.setSubmitting(false)
        },
    });
    useEffect(function () {
        initialValues = {}
        if (dataTypes.length > 0) {
            Object.keys(dataTypes[i].fields).map(field => {
                const field_type = dataTypes[i].fields[field]
                if (field_type === "str" || field_type === "int") {
                    initialValues[field] = ''
                } else {
                    initialValues['locname'] = ''
                    initialValues['longitude'] = ''
                    initialValues['latitude'] = ''

                }

            });
        }
        console.log(initialValues)
        formik.initialValues = initialValues
    })
    return (
        <FormikProvider value={formik}>
            <form onSubmit={formik.handleSubmit}>
                <label htmlFor="title">Title</label>
                <input
                    id="title"
                    name="title"
                    type="text"
                    onChange={formik.handleChange}
                    value={formik.values.name}
                />
                <label htmlFor="dataType">Data Type</label>
                <Field as="select" name="dataType" onChange={(event) => {
                    setI(event.target.value)
                }}>
                    {
                        dataTypes.length > 0 && dataTypes.map((type, index) => {
                            return (<option value={index}> {type.name}</option>)
                        })
                    }

                </Field>
                {
                    dataTypes.length > 0 &&
                    Object.keys(dataTypes[i].fields).map(field => {
                        const field_type = dataTypes[i].fields[field]
                        if (field_type === "str") {
                            return (
                                <div>
                                    <label htmlFor={field}>{field}</label>
                                    <input
                                        id={field}
                                        name={field}
                                        type="text"
                                        onChange={formik.handleChange}
                                        value={formik.values.name}
                                    />
                                </div>
                            )
                        } else if (field_type === "int") {
                            return <div>
                                <label htmlFor={field}>{field}</label>
                                <input
                                    id={field}
                                    name={field}
                                    type="number"
                                    onChange={formik.handleChange}
                                    value={formik.values.name}
                                />
                            </div>
                        } else if (field_type === "location") {
                            return (<div style={{marginTop: 10}}>
                                <h4>{field} </h4>
                                <label htmlFor="locname">Location Name</label>
                                <input
                                    id="locname"
                                    name="locname"
                                    type="text"
                                    onChange={formik.handleChange}
                                    value={formik.values.name}
                                />
                                <label htmlFor="longitude">Longitude</label>
                                <input
                                    id="longitude"
                                    name="longitude"
                                    type="number" step="0.01"
                                    onChange={formik.handleChange}
                                    value={formik.values.name}
                                />
                                <label htmlFor="latitude">Latitude</label>
                                <input
                                    id="latitude"
                                    name="latitude"
                                    type="number" step="0.01"
                                    onChange={formik.handleChange}
                                    value={formik.values.name}
                                />
                            </div>)
                        }

                    })
                }

                <button type="submit" style={{marginTop: 10}}>Submit</button>
            </form>
        </FormikProvider>

    )
        ;
};
