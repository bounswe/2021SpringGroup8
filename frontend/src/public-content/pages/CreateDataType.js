import React, {Component} from "react";
import * as Yup from 'yup';
import Navbar from "../Components/navbar";
import {Formik, Field} from "formik";
import AuthService from "../../services/auth.service";
import {withRouter} from "react-router-dom";
import DataTypeForm from "../Components/DataTypeForm"

class CreateDataType extends Component {
    constructor(props) {
        super(props);
        this.state = {
            communityId: '',
        }
    }

    componentDidMount() {
        this.setState({
            communityId :this.props.match.params.id
        })
        console.log(this.props.match.params.id);
    }

    render() {
        const {communityId} = this.state
        return (<>
            <Navbar/>
            <DataTypeForm communityId={communityId}/>
            {/*<Formik
                initialValues={{
                    fieldName1: '',
                    fieldType1: '',
                    fieldName2: '',
                    fieldType2: '',
                    fieldName3: '',
                    fieldType3: '',
                }}
                validationSchema={Yup.object().shape({
                    fieldName1: Yup.string()
                        .required('First Field is required'),
                    fieldType1: Yup.string()
                        .required('First Type is required'),
                })}
                onSubmit={(values, actions) => {

                    const fieldName1 = values.fieldName1;
                    const fieldType1 = values.fieldType1;
                    const fieldType2 = values.fieldType2;
                    const fieldName2 = values.fieldName2;
                    const fieldName3 = values.fieldName3;
                    const fieldType3 = values.fieldType3;
                    console.log(fieldName1)
                }}

                render={({values, touched, errors, isSubmitting, handleChange, handleBlur, handleSubmit}) => (
                    <div className="col-md-12">

                        <div className="card card-container">
                            <form onSubmit={handleSubmit}>
                                <label htmlFor="fieldName1">fieldName1</label>
                                <input
                                    name="fieldName1"
                                    type="text"
                                    placeholder="Enter your fieldName1"
                                    value={values.fieldName1}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {errors.fieldName1 && touched.fieldName1 && (
                                    <div className="input-feedback" style={{marginTop: 10}}>{errors.fieldName1}</div>
                                )}
                                <label htmlFor="fieldType1">fieldType1</label>
                                <Field as="select" name="fieldType1" class>
                                    <option value="str">String</option>
                                    <option value="int">Integer</option>
                                    <option value="location">Location</option>
                                </Field>

                                <label htmlFor="fieldName2">fieldName2</label>
                                <input
                                    name="fieldName2"
                                    type="text"
                                    placeholder="Enter your fieldName2"
                                    value={values.fieldName2}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                />
                                {errors.fieldName2 && touched.fieldName2 && (
                                    <div className="input-feedback">{errors.fieldName2}</div>
                                )}
                                <Field as="select" name="fieldType2" class>
                                    <option value="str">String</option>
                                    <option value="int">Integer</option>
                                    <option value="location">Location</option>
                                </Field>
                                <label htmlFor="fieldName3">fieldName3</label>
                                <input
                                    name="fieldName3"
                                    type="fieldName3"
                                    placeholder="Enter your fieldName3"
                                    value={values.fieldName3}
                                    onChange={handleChange}
                                    onBlur={handleBlur}
                                    className={errors.fieldName3 && touched.fieldName3 && "error"}
                                />
                                {errors.fieldName3 && touched.fieldName3 && (
                                    <div className="input-feedback">{errors.fieldName3}</div>
                                )}
                                <label htmlFor="fieldType3">fieldType3</label>
                                <Field as="select" name="fieldType3" class>
                                    <option value="str">String</option>
                                    <option value="int">Integer</option>
                                    <option value="location">Location</option>
                                </Field>
                                <button type="submit" disabled={isSubmitting}>Sign Up</button>
                            </form>
                        </div>
                    </div>

                )}
            />*/}
        </>)
    }
}


export default withRouter(CreateDataType)
