/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group15.ltm_moviesearcher.dto;

/**
 *
 * @author baoluan
 */
public class MovieFuture {
    private String movieName;
    private String imgUrl;
    private String id;
    private String dateRelease;
    private String urlDetail;
    private String weekRange;

    public MovieFuture(String movieName, String imgUrl, String id, String dateRelease, String urlDetail, String weekRange) {
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.id = id;
        this.dateRelease = dateRelease;
        this.urlDetail = urlDetail;
        this.weekRange = weekRange;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }
    
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public String getWeekRange() {
        return weekRange;
    }

    public void setWeekRange(String weekRange) {
        this.weekRange = weekRange;
    }
    
    @Override
    public String toString() {
        if("".equals(dateRelease)){
            return movieName + "movieFuture" + imgUrl + "movieFuture" + id + "movieFuture"+"reMovie"+"movieFuture" + urlDetail + "movieFuture" + weekRange;
        } else {
            return movieName + "movieFuture" + imgUrl + "movieFuture" + id + "movieFuture" + dateRelease + "movieFuture" + urlDetail + "movieFuture" + weekRange;
        }
    }
}
