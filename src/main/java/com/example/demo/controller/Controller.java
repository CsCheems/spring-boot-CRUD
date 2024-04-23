package com.example.demo.controller;

import com.example.demo.entity.Ciudadano;
import com.example.demo.entity.Domicilio;
import com.example.demo.service.CiudadanoService;
import com.example.demo.service.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private CiudadanoService ciudadanoService;
    @Autowired
    private DomicilioService domicilioService;

    @GetMapping({"/ciudadanos", "/"})
    public ModelAndView getCiudadanos(){
        List<Ciudadano> ciudadanos = ciudadanoService.getCiudadanos();
        ModelAndView modelAndView = new ModelAndView("ciudadanos");
        modelAndView.addObject("ciudadanos", ciudadanos);
        return modelAndView;
    }

    @GetMapping("/ciudadanos/{ciudadanoId}/domicilios")
    public ModelAndView getDomicilios(@PathVariable long ciudadanoId){
        List<Domicilio> domicilios = domicilioService.getDomiciliosPorCiudadano(ciudadanoId);
        Ciudadano ciudadano = ciudadanoService.getCiudadanoId(ciudadanoId);

        ModelAndView modelAndView = new ModelAndView("domicilios");
        modelAndView.addObject("domicilios", domicilios);
        modelAndView.addObject("ciudadano", ciudadano);
        return modelAndView;
    }



    @GetMapping("/formularioCiudadano")
    public ModelAndView formularioCiudadano(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("formularioCiudadano");
        return modelAndView;
    }

    @GetMapping("/formularioDomicilio")
    public ModelAndView formularioDomicilio(@RequestParam("ciudadanoId") long ciudadanoId) {
        System.out.println("ciudadanoId: " + ciudadanoId);
        ModelAndView modelAndView = new ModelAndView("formularioDomicilio");
        modelAndView.addObject("ciudadanoId", ciudadanoId);
        return modelAndView;
    }


    @PostMapping("/eliminarCiudadano/{ciudadanoId}")
    public ModelAndView eliminarCiudadano(@PathVariable long ciudadanoId){
        ciudadanoService.eliminar(ciudadanoId);
        return getCiudadanos();
    }

    @PostMapping("ciudadano/{ciudadanoId}/eliminarDomicilio/{domicilioId}")
    public ModelAndView eliminarDomicilio(@PathVariable long ciudadanoId, @PathVariable long domicilioId){
        domicilioService.eliminar(domicilioId);
        // Redirigir a la página de domicilios del ciudadano
        return getDomicilios(ciudadanoId);
    }

    @PostMapping("/crearCiudadano")
    public ModelAndView  creaCiudadano(@ModelAttribute Ciudadano ciudadano, @ModelAttribute Domicilio domicilio) {
        if (ciudadano.getDomicilios() == null) {
            System.out.println("La lista de domicilios está null");
            ciudadano.setDomicilios(new ArrayList<>());
        }
        Ciudadano ciudadanoGuardado = ciudadanoService.save(ciudadano);
        // Verifica si el objeto Ciudadano se ha guardado correctamente
        if (ciudadanoGuardado != null && ciudadanoGuardado.getId() != null) {
            Long ciudadanoId = ciudadanoGuardado.getId();
            System.out.println("Ciudadano guardado con ID: " + ciudadanoId);
            System.out.println(ciudadanoGuardado);
            // Asigna el ciudadano al domicilio
            domicilio.setCiudadano(ciudadanoGuardado);
            // Guarda el objeto Domicilio
            domicilioService.save(domicilio);
            // Redirige a la lista de ciudadanos
            return getCiudadanos();
        } else {
            System.out.println("Error: Ciudadano no se pudo guardar.");
            ModelAndView errorView = new ModelAndView("errorPage");
            errorView.addObject("mensajeError", "No se pudo guardar el ciudadano.");
            return errorView; // Retorna una vista de error
        }
    }

    @PostMapping("/crearDomicilio")
    public ModelAndView  creaDomicilio(@ModelAttribute Domicilio domicilio, @RequestParam("ciudadanoId") long ciudadanoId ) {
        Ciudadano ciudadano = ciudadanoService.getCiudadanoId(ciudadanoId);
        if(ciudadano != null){
            domicilio.setCiudadano(ciudadano);
            Domicilio domicilioGuardado = domicilioService.save(domicilio);

            if(domicilioGuardado != null){
                ModelAndView modelAndView = new ModelAndView("redirect:/ciudadanos/"+ciudadanoId+"/domicilios");
                return modelAndView;
            }else{
                System.out.println("Error: No se pudo guardar el domicilio.");
                ModelAndView errorView = new ModelAndView("errorPage");
                errorView.addObject("mensajeError", "No se pudo guardar el domicilio.");
                return errorView;
            }
        }else {
            // Si el ciudadano no existe
            System.out.println("Error: Ciudadano con ID " + ciudadanoId + " no encontrado.");
            ModelAndView errorView = new ModelAndView("errorPage");
            errorView.addObject("mensajeError", "Ciudadano no encontrado.");
            return errorView;
        }
    }


    @PostMapping("/ciudadano/{ciudadanoId}")
    public ModelAndView ciudadano(@PathVariable long ciudadanoId){
        Ciudadano ciudadano = ciudadanoService.getCiudadanoId(ciudadanoId);
        ModelAndView modelAndView = new ModelAndView("ciudadano");
        modelAndView.addObject("ciudadano",ciudadano);
        return modelAndView;
    }

    @PostMapping("/actualizarCiudadano/{ciudadanoId}")
    public ModelAndView actualizarCiudadano(@PathVariable long ciudadanoId, @ModelAttribute Ciudadano c){
        ciudadanoService.actualizar(c);
        return ciudadano(ciudadanoId);
    }

    @PostMapping("/actualizarDomicilio/{domicilioId}")
    public ModelAndView actualizarDomicilio(@PathVariable long domicilioId, @ModelAttribute Domicilio d, @ModelAttribute Ciudadano c){
        domicilioService.actualizar(d);
        return getDomicilios(domicilioId);
    }

    @PostMapping("/domicilio/{domicilioId}")
    public ModelAndView domicilio(@PathVariable long domicilioId){
        Domicilio domicilio = domicilioService.getDomiclioId(domicilioId);
        ModelAndView modelAndView = new ModelAndView("domicilio");
        modelAndView.addObject("domicilio",domicilio);
        return modelAndView;
    }



}
