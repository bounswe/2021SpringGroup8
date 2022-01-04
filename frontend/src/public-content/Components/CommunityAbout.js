import * as React from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';
import {Grid} from "@mui/material";

function CommunityAbout(props) {

    const {description, moderators, tags} = props;

    return (
        <div>


            <div style={{backgroundColor: "#e6ecf0", borderRadius: 10, margin: 10}}>
                <Typography variant="h6" style={{
                    fontWeight: 600,
                    borderTopLeftRadius: 10,
                    borderTopRightRadius: 10,
                    backgroundColor: "#5f99cf",
                    padding: 5,
                    marginTop: 5,
                    color: "white"
                }}>
                    About
                </Typography>
                <div>
                    <Typography paragraph style={{
                        paddingTop: 10,
                        paddingBottom: 10,
                        paddingInline: 10
                    }}>{description} </Typography>
                </div>

            </div>

            <div style={{backgroundColor: "#e6ecf0", borderRadius: 10, margin: 10, marginTop: 30}}>
                <Typography variant="h6" style={{
                    fontWeight: 600,
                    borderTopLeftRadius: 10,
                    borderTopRightRadius: 10,
                    backgroundColor: "#5f99cf",
                    padding: 5,
                    marginTop: 5,
                    color: "white"
                }}>
                    Tags
                </Typography>
                <Typography paragraph
                            style={{paddingTop: 10, paddingBottom: 10, paddingInline: 10}}>{tags} </Typography>
            </div>


            <div style={{backgroundColor: "#e6ecf0", borderRadius: 10, margin: 10, marginTop: 30}}>
                <Typography variant="h6" style={{
                    fontWeight: 600,
                    borderTopLeftRadius: 10,
                    borderTopRightRadius: 10,
                    backgroundColor: "#5f99cf",
                    padding: 5,
                    marginTop: 5,
                    color: "white"
                }}>
                    Moderators
                </Typography>
                <Typography paragraph style={{
                    paddingTop: 10,
                    paddingBottom: 10,
                    paddingInline: 10
                }}>{moderators} </Typography>
            </div>
        </div>
    );
}

CommunityAbout.propTypes = {
    description: PropTypes.string.isRequired,
};

export default CommunityAbout;
