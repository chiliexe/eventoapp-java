package com.chiliexe.eventoapp.controllers;

import com.chiliexe.eventoapp.models.Convidado;
import com.chiliexe.eventoapp.models.Evento;
import com.chiliexe.eventoapp.repository.ConvidadoRepository;
import com.chiliexe.eventoapp.repository.EventoRepository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {
    
    @Autowired
    private EventoRepository repository;
    @Autowired
    private ConvidadoRepository convidadoRepository;


    @GetMapping("/cadastrar-evento")
    public String form()
    {
        return "Evento/formEvento";
    }

    @PostMapping("/cadastrar-evento")
    public String form(@Valid Evento model, BindingResult result, RedirectAttributes attributes)
    {
    	if(result.hasErrors())
    	{
    		attributes.addFlashAttribute("msgError", "Verifique todos os campos");
    		return "redirect:/cadastrar-evento";
    	}
        repository.save(model);
        
        attributes.addFlashAttribute("msgSuccess", "Convidado adicionado com sucesso!!");
        return "redirect:/cadastrar-evento";
    }

    @GetMapping("/eventos")
    public ModelAndView eventos()
    {
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = repository.findAllByOrderByIdDesc();
        mv.addObject("eventos", eventos);
        
        return mv;
    }
    @GetMapping("/evento/deletar/{id}")
    public String deletar(@PathVariable int id, RedirectAttributes attributes)
    {
    	repository.deleteById(id);
		attributes.addFlashAttribute("msg", "Evento deletado com sucesso");
		return "redirect:/eventos";
    	
    }

    @GetMapping("/evento/{id}")
    public ModelAndView detalheEventos(@PathVariable int id)
    {
        Evento evento = repository.findById(id).get();
        ModelAndView mv = new ModelAndView("Evento/detalhes");
        List<Convidado> convidados = convidadoRepository.findByEvento(evento);
        mv.addObject("convidados", convidados);
        mv.addObject("evento", evento);
        return mv;
    }
    
    @PostMapping("/evento/{id}")
    public String detalheEventosPost(@PathVariable int id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attr)
    {
    	if(result.hasErrors()) {
    		attr.addFlashAttribute("msgError", "Verifique todos os campos do formulário");
    		return "redirect:/evento/{id}";
    	}
    	
        Evento evento = repository.findById(id).get();
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);
        attr.addFlashAttribute("msgSuccess", "Convidado adicionado com sucesso!!");
        return "redirect:/evento/{id}";
    }

}
