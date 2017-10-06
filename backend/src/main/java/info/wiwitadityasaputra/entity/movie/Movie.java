package info.wiwitadityasaputra.entity.movie;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.wiwitadityasaputra.entity.AbstractEntity;
import info.wiwitadityasaputra.entity.moviesearch.MovieSearch;

@Entity
@Table(name = "movie")
public class Movie extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "title")
	private String title;

	@Column(name = "imdb_id")
	private String imdbId;

	@Column(name = "imdb_rating")
	private Double imdbRating;

	@Column(name = "released")
	@Temporal(TemporalType.DATE)
	private Date released;

	@Column(name = "genre")
	private String genre;

	@Column(name = "year")
	private int year;

	@Column(name = "plot")
	private String plot;

	@Column(name = "json")
	private String json;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	private Set<MovieSearch> listMovieSearch;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Set<MovieSearch> getListMovieSearch() {
		return listMovieSearch;
	}

	public void setListMovieSearch(Set<MovieSearch> listMovieSearch) {
		this.listMovieSearch = listMovieSearch;
	}

}