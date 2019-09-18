package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

@Repository
public class ToppingRepository {

	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_L"));
		return topping;
	};
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Topping> load() {
		String sql = "SELECT id,name,price_m,price_l from toppings;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Topping> toppingList = template.query(sql, param, TOPPING_ROW_MAPPER);
		return toppingList;
	}
}
