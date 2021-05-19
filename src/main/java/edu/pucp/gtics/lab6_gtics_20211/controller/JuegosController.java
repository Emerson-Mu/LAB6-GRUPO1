package edu.pucp.gtics.lab6_gtics_20211.controller;

import edu.pucp.gtics.lab6_gtics_20211.entity.*;
import edu.pucp.gtics.lab6_gtics_20211.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller

public class JuegosController {

    @Autowired
    JuegosRepository juegosRepository;

    @Autowired
    PlataformasRepository plataformasRepository;

    @Autowired
    DistribuidorasRepository distribuidorasRepository;

    @Autowired
    GenerosRepository generosRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = {"/juegos/lista"})
    public String listaJuegos (Model model, Authentication auth){
        List<Juegos> listajuegos = juegosRepository.findAll(Sort.by("precio").ascending());
        User user =  userRepository.findByCorreo(auth.getName());
        model.addAttribute("listajuegos", listajuegos);
        System.out.println(user.getIdusuario());
        String rol ="";
        for (GrantedAuthority role : auth.getAuthorities()) {
            rol = role.getAuthority();
            break;
        }
        if (rol.equals("ADMIN")) {
            model.addAttribute("listajuegos", listajuegos);
            return "juegos/lista";
        }else if (rol.equals("USER")) {
            model.addAttribute("listajuegos", juegosRepository.obtenerJuegosPorUser(user.getIdusuario()));
            return "juegos/comprado";
        } else {
            model.addAttribute("listajuegos", listajuegos);
            return "juegos/vista";
        }
    }

    @GetMapping(value = {"", "/", "/vista"})
    public String vistaJuegos (Model model){
        List<Juegos> listajuegos = juegosRepository.findAll(Sort.by("nombre").descending());
        model.addAttribute("listajuegos", listajuegos);
        return "juegos/vista";
    }

    @GetMapping("/juegos/nuevo")
    public String nuevoJuegos(Model model, @ModelAttribute("juego") Juegos juego){
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
        List<Generos> listaGeneros = generosRepository.findAll();
        model.addAttribute("listaPlataformas", listaPlataformas);
        model.addAttribute("listaDistribuidoras", listaDistribuidoras);
        model.addAttribute("listaGeneros", listaGeneros);
        return "juegos/editarFrm";
    }

    @GetMapping("/juegos/editar")
    public String editarJuegos(@RequestParam("id") int id, Model model){
        Optional<Juegos> opt = juegosRepository.findById(id);
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
        List<Generos> listaGeneros = generosRepository.findAll();
        if (opt.isPresent()){
            Juegos juego = opt.get();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        }else {
            return "redirect:/juegos/lista";
        }
    }

    @PostMapping("/juegos/guardar")
    public String guardarJuegos(Model model, RedirectAttributes attr, @ModelAttribute("juego") @Valid Juegos juego, BindingResult bindingResult ){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            System.out.println("*************************");
            List<Plataformas> listaPlataformas = plataformasRepository.findAll();
            List<Distribuidoras> listaDistribuidoras = distribuidorasRepository.findAll();
            List<Generos> listaGeneros = generosRepository.findAll();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        } else {
            if (juego.getIdjuego() == 0) {
                attr.addFlashAttribute("msg", "Juego creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
            }
            juegosRepository.save(juego);
            return "redirect:/juegos/lista";
        }


    }

    @GetMapping("/juegos/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id){
        Optional<Juegos> opt = juegosRepository.findById(id);
        if (opt.isPresent()) {
            juegosRepository.deleteById(id);
        }
        return "redirect:/juegos/lista";
    }

}
