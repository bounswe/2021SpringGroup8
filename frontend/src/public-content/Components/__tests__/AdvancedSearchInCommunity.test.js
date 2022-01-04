import {fireEvent, getAllByTestId, getByTestId, render, screen} from '@testing-library/react';
import AdvancedSearchInCommunity from "../AdvancedSearchInCommunity";
import userEvent from "@testing-library/user-event";

test('find indicator showing user the data type selection box ', () => {
    render(<AdvancedSearchInCommunity />)
    const el = screen.getByText("Data Type")
    expect(el).toBeInTheDocument();
})


// test('try to click button which includes list of data types ', () => {
//     render(<AdvancedSearchInCommunity />)
//     const el = screen.getByRole('button', {name: 'Data Type'});
//     fireEvent.click(el)
//     const stringElement = screen.getByTestId("0")
//     expect(stringElement).toBeInTheDocument();
//     // expect(stringElement).toBeInTheDocument();
//     //expect((screen.getByText('Foo') as HTMLOptionElement).selected).toBeFalsy();
//     //const linkElement = screen.getByTestId("Data Type")
//     // expect(el).toBeInTheDocument();
//
//     //fireEvent.change(getByTestId('options'), { target: { value: 0 } })
//     //let options = getAllByTestId('options')
//     //expect(options[0].selected).toBeFalsy();
//
// })
