package com.luisdev.springboot.disneyAPI.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.luisdev.springboot.disneyAPI.models.dao.IPersonajeDao;
import com.luisdev.springboot.disneyAPI.models.entity.Personaje;

@Controller
public class PersonajeController {

	@Autowired
	private IPersonajeDao personajeDao;

	@GetMapping(value = "characters")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de personajes");
		model.addAttribute("personajes", personajeDao.findAll());
		return "characters";
	}

	@GetMapping(value = "/form")
	public String crearPersonaje(Map<String, Object> model) {
		Personaje personaje = new Personaje();
		model.put("titulo", "Formulario de Personaje");
		model.put("personaje", personaje);
		return "form";
	}
	
	@PostMapping(value = "/form")
	public String guardar(Personaje personaje) {
		personajeDao.save(personaje);
		return "redirect: characters";
	}
}
