package info.wiwitadityasaputra.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	public Movie findByImdbId(String imdbId);
}
