package com.example.demo.dao;

import com.example.demo.entity.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CiudadanoDao {

    JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }


    public Ciudadano save(Ciudadano ciudadano) {
        String query = "INSERT INTO ciudadanos (nombre, apellido, f_nacimiento, telefono, email) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        Long generatedId = template.queryForObject(query, new Object[]{
                ciudadano.getNombre(),
                ciudadano.getApellido(),
                new java.sql.Date(ciudadano.getF_nacimiento().getTime()),
                ciudadano.getTelefono(),
                ciudadano.getEmail()
        }, Long.class);

        ciudadano.setId(generatedId);
        return ciudadano;

    }


    public List<Ciudadano> getCiudadanos() {
        return template.query("SELECT * FROM ciudadanos", new ResultSetExtractor<List<Ciudadano>>() {
            @Override
            public List<Ciudadano> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Ciudadano> list = new ArrayList<Ciudadano>();
                while (rs.next()) {
                    Ciudadano c = new Ciudadano();
                    c.setId(rs.getLong("id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setApellido(rs.getString("apellido"));
                    c.setF_nacimiento(rs.getDate("f_nacimiento"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setEmail(rs.getString("email"));
                    list.add(c);
                }
                return list;
            }
        });
    }


    public Ciudadano getCiudadanoId(long id) {
        return template.query("SELECT * FROM ciudadanos WHERE ID = ?", new ResultSetExtractor<Ciudadano>() {

            public Ciudadano extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Ciudadano c = new Ciudadano();
                    c.setId(rs.getLong("id"));
                    c.setNombre(rs.getString("nombre"));
                    c.setApellido(rs.getString("apellido"));
                    c.setF_nacimiento(rs.getDate("f_nacimiento"));
                    c.setTelefono(rs.getString("telefono"));
                    c.setEmail(rs.getString("email"));
                    return c;
                }
                return null;
            }
        }, id);
    }


    public void actualiza(Ciudadano ciudadano) {
        String query = "UPDATE ciudadanos SET nombre = ?, apellido = ?, f_nacimiento = ?, telefono = ?, email = ? WHERE id = ?";
        System.out.println(query);
        template.update(query, ciudadano.getNombre(), ciudadano.getApellido(), ciudadano.getF_nacimiento(), ciudadano.getTelefono(), ciudadano.getEmail(), ciudadano.getId());
    }


    public void eliminar(long id) {
        String query = "DELETE FROM ciudadanos WHERE ID = ?";
        template.update(query, id);
    }
}