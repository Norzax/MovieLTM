package group15.ltm_moviesearcher.dto;

import java.io.Serializable;

/**
 *
 * @author baoluan
 */
public class MovieDetail implements Serializable{
    private String id;
    private String description;
    private String urlTomato;
    private String actor;
    private String director;
    private String productor;
    private String imgUrl;
    private String movieName;
    private String movieNameType;
    private String urlTrailer;
    private String dateRelease;
    private String duration;
    private String userAge;

    public MovieDetail(String id, String description, String urlTomato, String actor, String director, String productor, String imgUrl, String movieName, String movieNameType, String urlTrailer, String dateRelease, String duration, String userAge) {
        this.id = id;
        this.description = description;
        this.urlTomato = urlTomato;
        this.actor = actor;
        this.director = director;
        this.productor = productor;
        this.imgUrl = imgUrl;
        this.movieName = movieName;
        this.movieNameType = movieNameType;
        this.urlTrailer = urlTrailer;
        this.dateRelease = dateRelease;
        this.duration = duration;
        this.userAge = userAge;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlTomato() {
        return urlTomato;
    }

    public void setUrlTomato(String urlTomato) {
        this.urlTomato = urlTomato;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieNameType() {
        return movieNameType;
    }

    public void setMovieNameType(String movieNameType) {
        this.movieNameType = movieNameType;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    @Override
    public String toString() {
        return id + "movieDetail" + description + ", movieDetail" + urlTomato + "movieDetail" + actor + "movieDetail" + director + "movieDetail" + productor + "movieDetail" + imgUrl + "movieDetail" + movieName + "movieDetail" + movieNameType + "movieDetail" + urlTrailer + "movieDetail" + dateRelease + "movieDetail" + duration + "movieDetail" + userAge;
    } 
}
