import React, {useEffect, useState} from 'react';
import {useFormik, Field, Form, Formik, FormikProps, FormikProvider} from 'formik';

import {useHistory} from "react-router-dom";
import {GoogleMap, Marker, useJsApiLoader} from '@react-google-maps/api';
import ProfileService from "../services/profile.service";


export default function ProfileCityEdit(props) {
    const {dataTypes} = props;
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
        ProfileService.updateCity(formik.values.locname, formik.values.longitude, formik.values.latitude).then(response => {
            console.log(response)
            if (response.data.success === "True") {

                alert(JSON.stringify({"message": "successfully created post"}, null, 2));
                //window.location.reload(false);

            }else{
                alert(JSON.stringify({"message": response.data['@error']}, null, 2));
            }
        }).catch(error => {
            console.log(error)
        })
    }, [])

    let initialValues = {}
    // if (dataTypes.length >= 0) {
    //     Object.keys(dataTypes[i].fields).map(field => {
    //         const field_type = dataTypes[i].fields[field]
    //         initialValues['locname'] = ''
    //         initialValues['longitude'] = ''
    //         initialValues['latitude'] = ''
    //     });
    // }
    const formik = useFormik({
        initialValues: initialValues,
        onSubmit: (values, actions) => {
            let request_json = {}
            Object.keys(dataTypes[i].fields).map(field => {
                request_json[field] = {
                    locname: values.locname,
                    longitude: values.longitude,
                    latitude: values.latitude
                }
            }
            );
            console.log("request_json")
            console.log(request_json)
            ProfileService.updateCity(formik.values.locname, values.longitude, values.latitude).then(response => {
                console.log(response)
                if (response.data.success === "True") {

                    alert(JSON.stringify({"message": "successfully created post"}, null, 2));
                    console.log("successfully updated the city")
                    //window.location.reload(false);

                }else{
                    alert(JSON.stringify({"message": response.data['@error']}, null, 2));
                }
            }).catch(error => {
                console.log(error)
            })

        },
    });
    useEffect(function () {
        initialValues = {}
        initialValues['locname'] = ''
        initialValues['longitude'] = ''
        initialValues['latitude'] = ''
        //console.log(initialValues)
        formik.initialValues = initialValues
    })
    return (
        <FormikProvider value={formik}>
            <form onSubmit={formik.handleSubmit}>

                             <div style={{marginTop: 10}}>
                                <label htmlFor="locname">City Name</label>
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
                                    <>
                                        <Marker position={center}>
                                        </Marker>
                                    </>
                                </GoogleMap>
                            </div>
                <button type="submit" style={{marginTop: 10}}>Submit</button>
            </form>
        </FormikProvider>
    );
};
