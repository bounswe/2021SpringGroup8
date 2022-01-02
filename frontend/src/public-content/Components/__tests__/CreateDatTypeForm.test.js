import {render, screen} from '@testing-library/react';
import CreateDataTypeFrom from '../CreateDataTypeForm';

test('test basic create data type form ', () => {
    render(<CreateDataTypeFrom />)
    const linkElement = screen.getByTestId("0#fieldName")
    expect(linkElement).toBeInTheDocument();
})

test('test basic create data type form when add field button pressed ', () => {
    render(<CreateDataTypeFrom />)
    const linkElement = screen.getByTestId("0#addFieldGroup")
    linkElement.click()
    const linkNewElement = screen.getByTestId("1#addFieldGroup")
    expect(linkNewElement).toBeInTheDocument();
})

test('test basic create data type form when add field button pressed ', () => {
    render(<CreateDataTypeFrom />)
    const linkElement = screen.getByTestId("0#removeFieldGroup")
    linkElement.click()
    expect(linkElement).not.toBeInTheDocument();
})