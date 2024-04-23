package com.example.demo.dao;

import com.example.demo.entity.Domicilio;
import com.example.demo.entity.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DomicilioDao{

    JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource){
        template = new JdbcTemplate(dataSource);
    }


    public Domicilio save(Domicilio domicilio) {

        System.out.println("Guardando domicilio con la siguiente información:");
        System.out.println("Ciudadano ID: " + (domicilio.getCiudadano() != null ? domicilio.getCiudadano().getId() : "N/A"));
        System.out.println("Dirección: " + domicilio.getDireccion());
        System.out.println("Ciudad: " + domicilio.getCiudad());
        System.out.println("Código Postal: " + domicilio.getCodigoPostal());
        System.out.println("País: " + domicilio.getPais());

        String query = "INSERT INTO domicilios (ciudadano_id, direccion, ciudad, codigoPostal, pais) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Long generatedId = template.queryForObject(query, new Object[]{
                domicilio.getCiudadano().getId(),
                domicilio.getDireccion(),
                domicilio.getCiudad(),
                domicilio.getCodigoPostal(),
                domicilio.getPais()
        }, Long.class);

        domicilio.setId(generatedId);
        return domicilio;
    }


    public void actualiza(Domicilio domicilio) {
        String query = "UPDATE domicilios SET direccion = ?, ciudad = ?, codigoPostal = ?, pais = ? WHERE id = ?";
        System.out.println(query);
        template.update(query, domicilio.getDireccion(), domicilio.getCiudad(), domicilio.getCodigoPostal(), domicilio.getPais(), domicilio.getId());
    }


    public List<Domicilio> getDomiciliosPorCiudadano(long id) {
        return template.query("SELECT * FROM domicilios WHERE ciudadano_id  = ?", new Object[]{id}, new ResultSetExtractor<List<Domicilio>>() {
            @Override
            public List<Domicilio> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Domicilio> list = new ArrayList<Domicilio>();
                while (rs.next()) {
                    Domicilio domicilio = new Domicilio();
                    domicilio.setId(rs.getLong("id"));
                    domicilio.setDireccion(rs.getString("direccion"));
                    domicilio.setCiudad(rs.getString("ciudad"));
                    domicilio.setCodigoPostal(rs.getString("codigoPostal"));
                    domicilio.setPais(rs.getString("pais"));
                    list.add(domicilio);
                }
                return list;
            }
        });
    }

    public Domicilio getDomicilioId(long id) {
        return template.query("SELECT * FROM domicilios WHERE ID = ?", new ResultSetExtractor<Domicilio>() {
            @Override
            public Domicilio extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    Domicilio d = new Domicilio();
                    d.setId(rs.getLong("id"));
                    d.setDireccion(rs.getString("direccion"));
                    d.setCiudad(rs.getString("ciudad"));
                    d.setCodigoPostal(rs.getString("codigoPostal"));
                    d.setPais(rs.getString("pais"));
                    return d;
                }
                return null;
            }
        }, id);
    }


    public void eliminar(long id) {
        String query = "DELETE FROM domicilios WHERE ID = ?";
        template.update(query, id);
    }

}