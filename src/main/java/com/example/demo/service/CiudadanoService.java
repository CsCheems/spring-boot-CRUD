package com.example.demo.service;

import com.example.demo.dao.CiudadanoDao;
import com.example.demo.entity.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadanoService {

    @Autowired
    private CiudadanoDao ciudadanoDao;

    public Ciudadano save(Ciudadano ciudadano){
        return ciudadanoDao.save(ciudadano);
    }

    public List<Ciudadano> getCiudadanos(){
        return ciudadanoDao.getCiudadanos();
    }

    public Ciudadano getCiudadanoId(Long id){
        return ciudadanoDao.getCiudadanoId(id);
    }

    public void actualizar(Ciudadano c){
        ciudadanoDao.actualiza(c);
    }

    public void eliminar(long id){
        ciudadanoDao.eliminar(id);
    }

}
