package ibf2022.assessment.paf.batch3.repositories;

import java.util.List;
import java.util.Optional;

import ibf2022.assessment.paf.batch3.models.Beer;
import ibf2022.assessment.paf.batch3.models.Brewery;
import ibf2022.assessment.paf.batch3.models.Style;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BeerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

	// DO NOT CHANGE THE SIGNATURE OF THIS METHOD
	public List<Style> getStyles() {
		// TODO: Task 2
        String query = "SELECT styles.id AS styleId, styles.style_name AS name, COUNT(beers.id) AS beerCount " +
                       "FROM styles " +
                       "LEFT JOIN beers ON styles.id = beers.style_id " +
                       "GROUP BY styles.id, styles.style_name " +
                       "ORDER BY beerCount DESC, styles.style_name ASC";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Style.class));
	}
		
	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public List<Beer> getBreweriesByBeer(int styleId) {
		// TODO: Task 3
        String query = "SELECT beers.id AS beerId, beers.name AS beerName, beers.descript AS beerDescription, " +
                       "breweries.id AS breweryId, breweries.name AS breweryName " +
                       "FROM beers " +
                       "JOIN breweries ON beers.brewery_id = breweries.id " +
                       "WHERE beers.style_id = ? " +
                       "ORDER BY beers.name ASC";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Beer.class), styleId);
	}

	// DO NOT CHANGE THE METHOD'S NAME OR THE RETURN TYPE OF THIS METHOD
	public Optional<Brewery> getBeersFromBrewery(int breweryId) {
		// TODO: Task 4
		String breweryQuery = "SELECT * FROM breweries WHERE id = ?";
		List<Brewery> breweryList = jdbcTemplate.query(breweryQuery, new BeanPropertyRowMapper<>(Brewery.class), breweryId);

		if (!breweryList.isEmpty()) {
			Brewery brewery = breweryList.get(0);
			String beerQuery = "SELECT id AS beerId, name AS beerName, descript AS beerDescription FROM beers WHERE brewery_id = ? ORDER BY name ASC";
			List<Beer> beers = jdbcTemplate.query(beerQuery, new BeanPropertyRowMapper<>(Beer.class), breweryId);
			brewery.setBeers(beers);
			return Optional.of(brewery);
		}

		return Optional.empty();
	}
}
