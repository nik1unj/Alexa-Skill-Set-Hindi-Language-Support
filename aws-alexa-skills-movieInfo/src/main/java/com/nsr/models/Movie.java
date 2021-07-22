package com.nsr.models;


public class Movie {

	private String movieId;
	private String movieName;
	private String movieDesc;
	private Integer releaseYear;

	public Movie() {
	}

	public Movie(String movieId, String movieName, String movieDesc, Integer releaseYear) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieDesc = movieDesc;
		this.releaseYear = releaseYear;
	}

	public String getMovieDesc() {
		return movieDesc;
	}

	public void setMovieDesc(String movieDesc) {
		this.movieDesc = movieDesc;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", movieName=" + movieName + ", movieDesc=" + movieDesc + ", releaseYear="
				+ releaseYear + "]";
	}

}
