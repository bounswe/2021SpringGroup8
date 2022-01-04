import { render, screen } from '@testing-library/react';
import CommunityAbout from "../CommunityAbout";
import CommunityPosts from "../CommunityPosts";

test('test community about section with example description', () => {
    render(<CommunityPosts description={"description1"}/>)
    const linkElement = screen.getByText(/moderators2/i);
    expect(linkElement).toBeInTheDocument();
})

test('test community about section with example tag', () => {
    render(<CommunityPosts description={"description2"} tags={"moderators2"}/>)
    const linkElement = screen.getByText(/moderators2/i);
    expect(linkElement).toBeInTheDocument();
})
