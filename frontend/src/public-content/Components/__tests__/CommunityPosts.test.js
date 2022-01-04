import { render, screen} from "@testing-library/react";
import CommunityPosts from "../CommunityPosts";

test(' if there is no post exist in community return no results ', () => {
    render(<CommunityPosts posts={[]}/>)
    const elem = screen.getByText("No Results Found")
    expect(elem).toBeInTheDocument()

})

test(' Check the whether or not post shows  its title ', () => {
    render(<CommunityPosts posts={[ {postTitle: "My Post Title",
                                   description: "description",
        postedBy:{

            id:"61d342ba645569c8a263d4e6",
            pplink:null,
            username:"mustafa can aydin",


        }}]}/>)
    const elem = screen.getByText("My Post Title")
    expect(elem).toBeInTheDocument()

})

test(' Check the whether or not post shows its description ', () => {
    render(<CommunityPosts posts={[ {postTitle: "My Post Title",
        description: "description",
        postedBy:{

            id:"61d342ba645569c8a263d4e6",
            pplink:null,
            username:"mustafa can aydin",


        }}]}/>)
    const elem = screen.getByText("mustafa can aydin")
    expect(elem).toBeInTheDocument()

})

test(' Check the whether or not post shows the name of its creator  ', () => {
    render(<CommunityPosts posts={[ {postTitle: "My Post Title",
        description: "My Description",
        postedBy:{

            id:"61d342ba645569c8a263d4e6",
            pplink:null,
            username:"mustafa can aydin",


        }}]}/>)
    const elem = screen.getByText("mustafa can aydin")
    expect(elem).toBeInTheDocument()

})
