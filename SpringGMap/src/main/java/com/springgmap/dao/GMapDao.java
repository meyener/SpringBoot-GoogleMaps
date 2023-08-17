package com.springgmap.dao;

import com.springgmap.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GMapDao implements DAO<Restaurant> {


    private static final Logger LOGGER= LoggerFactory.getLogger(GMapDao.class);
    private final JdbcTemplate jdbcTemplate;

    public GMapDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //Mapper
    RowMapper<Restaurant> rowMapper=(rs,rowNum)->{
      Restaurant restaurant=new Restaurant();
      restaurant.setId(rs.getInt("id"));
      restaurant.setLat(rs.getDouble("lat"));
      restaurant.setLng(rs.getDouble("lng"));
      restaurant.setRestaurantId(rs.getString("restaurantId"));
      restaurant.setName(rs.getString("name"));
      restaurant.setTypes(rs.getString("types"));
      restaurant.setVicinity(rs.getString("vicinity"));
      return restaurant;
    };

    @Override
    public List<Restaurant> list() {
        String sql="SELECT id,lat,lng,restaurantId,name,types,vicinity  FROM restaurant";
        return jdbcTemplate.query(sql,rowMapper);

    }

    @Override
    public void create(Restaurant restaurant) {
        String sql="INSERT INTO restaurant(lat,lng,restaurantId,name,types,vicinity) values(?,?,?,?,?,?)";

        int insert=jdbcTemplate.update(
                sql,
                restaurant.getLat(),
                restaurant.getLng(),
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getTypes(),
                restaurant.getVicinity());

        if(insert==1){
            LOGGER.info("Restaurant added = "+restaurant.getName());
        }
    }

    @Override
    public Restaurant get(int id) {
        String sql="SELECT id,lat,lng,restaurantId,name,types,vicinity FROM restaurant WHERE id= ?";

        Restaurant restaurant=null;

        try {
            restaurant=jdbcTemplate.queryForObject(sql,new Object[]{id},rowMapper);
        }catch(DataAccessException e){
            LOGGER.info("NOT FOUND ");
        }

        return restaurant;
    }

    /*@Override
    public void update(Restaurant restaurant, int id) {
        String sql="update restaurant set lat=?, set lng=?, set name=?,set address=? where restaurant_id=?";
        int update=jdbcTemplate.update(sql,restaurant.getName(),restaurant.getAddress(),rowMapper);

    }*/

    @Override
    public void delete(int id) {

        jdbcTemplate.update("DELETE FROM restaurant WHERE id=?",id);
    }
}
