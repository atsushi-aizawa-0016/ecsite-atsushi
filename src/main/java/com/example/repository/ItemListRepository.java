package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

@Repository
public class ItemListRepository {

		private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
			Item item = new Item();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setDescription(rs.getString("description"));
			item.setPriceM(rs.getInt("price_m"));
			item.setPriceL(rs.getInt("price_l"));
			item.setImagePath(rs.getString("image_path"));
			item.setDeleted(rs.getBoolean("deleted"));
			return item;
		};
		
		@Autowired
		private NamedParameterJdbcTemplate template;
		
		public List<Item> findAll(){
			String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted from items ORDER BY price_m DESC;";
			SqlParameterSource param = new MapSqlParameterSource();
			List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
			return itemList;
		}
		
		public List<Item> findByName(String searchName){
			String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted from items WHERE name like :name ORDER BY price_m DESC;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("name",("%"+ searchName+"%"));
			List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
			return itemList;
		}
}
