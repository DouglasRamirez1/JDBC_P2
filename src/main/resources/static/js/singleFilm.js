
// the action url for reviewForm to execute when submitting the form
const actionURL = `${window.location.href.substr(0, window.location.href.indexOf('?'))}`;

// the api url that returns single film with current film title in the endpoint
const infoURL = "http://" + window.location.hostname + ":" + window.location.port + window.location.pathname + "/detail";

// the api url that returns all associated reviews of the current film
const reviewsURL = "http://" + window.location.hostname + ":" + window.location.port + window.location.pathname + "/reviews";

// the function that populates the HTML page with the film object fetched from infoURL
function populateFilm(film){
    // populate the content of predefined div with film information
    document.getElementById("singleFilm").innerHTML = `<div class="innerWrapper">
                         <div class="image">
                             <img src="../../images/${film.title}.jpg" style="
                                             height: 360px;
                                             width: relative;">
                         </div>
                         <section class="detailInfo">
                             <h4 style="font-size: 24px;">
                                 Directed by: ${film.director}
                             </h4>
                             <hr>
                             <p style="font-size: 24px;">${film.description}</p>
                             <p style="font-size: 24px;">
                                 Year of Publication: <strong>${film.release_date}</strong>
                                 &#8226; Movie Length: <strong>${film.running_time} minutes</strong>
                             </p>
                         </section>
                     </div>`;
    // change the page title to film title
    document.getElementById("filmName").innerHTML = `${film.title}`;
}

// the function that populates the HTML with reviews of the current film fetched from reviewsURL
function populateReviews(reviews) {
    // loop over the data to append every review to the page
    for(review of reviews) {
        // create element to be append to the page
        let post = document.createElement("div")
        post.className = "reviewContent";
        post.innerHTML = `<div class="innerReviewContent">
                          <h4>by: ${review.user.username} &emsp; rating: ${review.rating}</h4>
                            <hr>
                          <p>${review.content}</p>
                          </div>
                          <form action="/films/title/${review.film.title}/${review.reviewId}" method="GET">
                              <input type="submit" value="Comments"/>
                          </form>`;
        // append to the static div predefined in the HTML page
        document.getElementById("filmReviews").append(post);
    }
}
function reload() { window.location.reload() };

// performs nested fetch that populates the HTML page
// with one film and all reviews of that review
// when the web page loads
(()=>{
    console.log(window.location.href + "/detail");
    console.log(window.location.href + "/reviews");
    fetch(infoURL)
        .then((res) => res.json())
        .then((film) => {
            console.log(film);
            populateFilm(film)
            fetch(reviewsURL)
                .then((res) => res.json())
                .then((reviews) => populateReviews(reviews));
        })
    document.getElementById("reviewForm").action = actionURL;
})();