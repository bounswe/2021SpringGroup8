import {fireEvent, render, screen} from '@testing-library/react';
import ProfileBar from '../profilebar';

test('test basic create data type form ', () => {
    render(<ProfileBar />)
    const linkElement = screen.getByTestId("profile-link")
    fireEvent.click(linkElement);
    const textEmail = screen.getByText("email");
    expect(textEmail).toBeInTheDocument();
})
