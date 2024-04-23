package com.example.demo.service;

import com.example.demo.dao.DomicilioDao;
import com.example.demo.entity.Ciudadano;
import com.example.demo.entity.Domicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomicilioService {

    @Autowired
    private DomicilioDao domicilioDao;

    public Domicilio save(Domicilio domicilio){
        return domicilioDao.save(domicilio);
    }

    public void actualizar(Domicilio domicilio){
        domicilioDao.actualiza(domicilio);
    }

    public Domicilio getDomiclioId(long id){
        return domicilioDao.getDomicilioId(id);
    }

    public List<Domicilio> getDomiciliosPorCiudadano(long id){
        return domicilioDao.getDomiciliosPorCiudadano(id);
    }

    public void eliminar(long id){
        domicilioDao.eliminar(id);
    }

}