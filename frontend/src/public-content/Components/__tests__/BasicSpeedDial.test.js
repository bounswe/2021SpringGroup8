import { render, screen } from '@testing-library/react';
import BasicSpeedDial from '../BasicSpeedDial';

test('test basic speed dial', () => {
    render(<BasicSpeedDial owner={false}/>)
    const linkElement = screen.getByTestId("post")
    expect(linkElement).toBeInTheDocument();
})

test('test basic speed dial owner', ()=>{
    render(<BasicSpeedDial owner={true}/>)
    const linkElement = screen.getByTestId("type")
    expect(linkElement).toBeInTheDocument();
})