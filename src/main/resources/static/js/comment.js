
// the action url for commentForm to execute when submitting the form
const actionURL = `${window.location.href.substr(0, window.location.href.indexOf('?'))}`;

// the api url that returns single review with current reviewId in the endpoint
const reviewURL = "http://" + window.location.hostname + ":" + window.location.port + window.location.pathname + "/review";

// the api url that returns all associated comments of the current review
const commentURL = "http://" + window.location.hostname + ":" + window.location.port + window.location.pathname + "/comments";

// the function that populates the HTML page with the review object fetched from reviewURL
function populateReview(review){
    document.getElementById("currentReview").innerHTML =
    `<div class="reviewContent">
        <div class="innerReviewContent">
            <h4>by: ${review.user.username} &emsp; rating: ${review.rating}</h4>
            <hr>
            <p>${review.content}</p>
        </div>
    </div>`;
}

// the function that populates the HTML with comments of the current review fetched from commentURL
function populateComments(comments) {
    // loop over the data to append every comment to the page
    for(comment of comments) {
        // create element to be append to the page
        let post = document.createElement('div');
        post.className = "commentContent";
        post.innerHTML = `<div class="innerCommentContent">
                                <h4>${comment.user.username}</h4>
                                <hr>
                                <p>${comment.content}</p>
                            </div>`
        // append to the static div predefined in the HTML page
        document.getElementById("allComments").append(post);
    }
}

// performs nested fetch that populates the HTML page
// with one review and all comments of that review
// when the web page loads
window.onload = () => {
    console.log(window.location.href + "/review");
    console.log(window.location.href + "/comments");
    fetch(reviewURL)
        .then((res) => res.json())
        .then((review) => {
            populateReview(review)
            fetch(commentURL)
                .then((res) => res.json())
                .then((comments) => populateComments(comments));
        })
    document.getElementById("commentForm").action = actionURL;
};