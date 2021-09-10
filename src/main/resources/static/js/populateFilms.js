
// external api url with information of Ghibli Studio films
const apiURL = "https://ghibliapi.herokuapp.com/films"

// the function that populates the HTML page with film objects fetched from apiURL
function populatePage(films){
    // loop over the data to append every film to the page
    for(obj of films){
        // create element to be append to the page
        let film = document.createElement('div');
        film.innerHTML = `<div class="card">
                              <center>
                              <form action="/films/title/${obj.title}" method="GET">
                                  <img src="images/${obj.title}.jpg" style="
                                            height: 360px;
                                            width: relative;">
                                  <br>
                                  <input type="submit" value="More"/>
                              </form>
                              </center>
                          </div>`;
        // append to the static div predefined in the HTML page
        document.getElementById("films-container").append(film);
    }
}
// performs fetch that populates the HTML page
// with films fetched from the external api
// when the web page loads
(()=>{
    fetch(apiURL)
        .then((res) => res.json())
        .then((films) => populatePage(films));
})();