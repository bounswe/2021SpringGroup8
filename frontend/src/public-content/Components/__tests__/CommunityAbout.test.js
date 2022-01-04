import { render, screen } from '@testing-library/react';
import CommunityAbout from "../CommunityAbout";

test('test community about section with example description', () => {
    render(<CommunityAbout description={"example description"}/>)
    const linkElement = screen.getByText(/example description/i);
    expect(linkElement).toBeInTheDocument();
})

test('test community about section with example tag', () => {
    render(<CommunityAbout description={"example description"} tags={"example tag"}/>)
    const linkElement = screen.getByText(/example tag/i);
    expect(linkElement).toBeInTheDocument();
})

test('test community about section with example moderator', () => {
    render(<CommunityAbout description={"example description"} tags={"example tag"} moderators={"example moderators"}/>)
    const linkElement = screen.getByText(/example moderators/i);
    expect(linkElement).toBeInTheDocument();
})
