import React, {useEffect, useState} from 'react';
import {useFormik, Field, Form, Formik, FormikProps, FormikProvider} from 'formik';
import PostService from '../../services/post.service'
import {useHistory} from "react-router-dom";
import {GoogleMap, Marker, useJsApiLoader} from '@react-google-maps/api';


export default function CreatePostForm(props) {
    const {communityId, dataTypes} = props;
    const history = useHistory();
    const [i, setI] = useState(0);
    const containerStyle = {
        width: '100%',
        height: '400px'
    };
    const center_marker = {
        lat: 0,
        lng: 0
    };
    const {isLoaded} = useJsApiLoader({
        googleMapsApiKey: "AIzaSyCc8jB_Zj8rqPkrX1o-aNG4aLYjDbblTPI"
    })
    const [map, setMap] = React.useState(null)
    const [center, setCenter] = React.useState(center_marker);

    const onLoad = React.useCallback(function callback(map) {
        setMap(map)
    }, [])

    const onUnmount = React.useCallback(function callback(map) {
        setMap(null)
    }, [])

    const onClick = React.useCallback(function callback(map) {
        setCenter({lat: map.latLng.lat(), lng: map.latLng.lng()});
        formik.setFieldValue("latitude", map.latLng.lat());
        formik.setFieldValue("longitude", map.latLng.lng());
        formik.values.latitude = map.latLng.lat();
        formik.values.longitude = map.latLng.lng();
    }, [])

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
                            locname: values.locname,
                            longitude: values.longitude,
                            latitude: values.latitude
                        }
                    }

                });
                PostService.submitPost(communityId, title, datatypename, request_json).then(response => {
                    console.log(response)
                    if (response.data.success === "True") {
                        actions.setSubmitting(false);
                        alert(JSON.stringify({"message": "successfully created post"}, null, 2));
                        return history.push('/community/' + communityId);
                    }
                    alert(JSON.stringify({"message": response.data['@error']}, null, 2));
                    actions.setSubmitting(false)

                }).catch(error => {
                    console.log(error)
                })
            }

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
                                    value={formik.values.locname}
                                />
                                <label htmlFor="longitude">Longitude</label>
                                <input
                                    id="longitude"
                                    name="longitude"
                                    type="number" step="0.0000000001"
                                    onChange={formik.handleChange}
                                    value={formik.values.longitude}
                                />
                                <label htmlFor="latitude">Latitude</label>
                                <input
                                    id="latitude"
                                    name="latitude"
                                    type="number" step="0.0000000001"
                                    onChange={formik.handleChange}
                                    value={formik.values.latitude}
                                />
                                <GoogleMap
                                    mapContainerStyle={containerStyle}
                                    defaultCenter={center}
                                    zoom={5}
                                    center={center}
                                    onLoad={onLoad}
                                    onUnmount={onUnmount}
                                    onClick={onClick}
                                >
                                    { /* Child components, such as markers, info windows, etc. */}
                                    <>
                                        <Marker position={center}>
                                        </Marker>
                                    </>
                                </GoogleMap>

                            </div>)
                        }

                    })
                }

                <button type="submit" style={{marginTop: 10}}>Submit</button>
            </form>
        </FormikProvider>

    );
};
