import {fireEvent, render, screen} from '@testing-library/react';
import Profile from "../profile"

test('initial state of  button edit for the email ', () => {

    render(<Profile />)
    //screen has access to virtual dom created by render
    // 2nd argument is options :
    // find an element with the role of a button and text of change to blue
    // const colorButton = screen.getByRole('button', {name:'Change to blue'});
    //
    // //expect the bg color to be red
    // expect(colorButton).toHaveStyle({backgroundColor:'red'})

    const emailEditButton = screen.getByTestId("email.edit")
    const updateEmailButton = screen.getByRole("button", {name:'Update Email'})
    fireEvent.click(emailEditButton);

    expect(updateEmailButton).toBeInTheDocument();


});
