import React from 'react';
import {GoogleMap, Marker, useJsApiLoader} from '@react-google-maps/api';

function Map(props) {
    const {marker} = props;
    const containerStyle = {
        width: "100%",
        height: '400px'
    };

    const center_marker = {
        lat: marker.lat,
        lng: marker.lng
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
        setCenter({lat:map.latLng.lat(), lng: map.latLng.lng() })
    }, [])

    return isLoaded ? (
        <GoogleMap
            mapContainerStyle={containerStyle}
            defaultCenter={center}
            zoom={10}
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
    ) : <></>
}

export default React.memo(Map)




