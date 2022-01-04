import { render, screen} from "@testing-library/react";

import CommunityPost from "../CommunityPost";


test(' Check the whether or not post shows the name of its creator  ', () => {
    render(<CommunityPost username={
            "mustafa can aydin"
        }/>)
    const elem = screen.getByText("@mustafa can aydin")
    expect(elem).toBeInTheDocument()

})
