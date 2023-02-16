package com.luisdev.springboot.disneyAPI.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.net.HttpHeaders;
import com.luisdev.springboot.disneyAPI.models.dao.IPersonajeDao;
import com.luisdev.springboot.disneyAPI.models.entity.Personaje;
import com.luisdev.springboot.disneyAPI.models.service.IPersonajeService;
import com.luisdev.springboot.disneyAPI.models.service.IUploadService;

@Controller
@SessionAttributes("personaje")
public class PersonajeController {

	@Autowired
	private IPersonajeDao personajeDao;

	@Autowired
	private IPersonajeService personajeService;

	@Autowired
	private IUploadService uploadService;

	@GetMapping(value = "/upload/{filename:.+}")
	public ResponseEntity<Resource> verImagen(@PathVariable String filename) {

		Resource recurso = null;
		try {

			recurso = uploadService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "characters")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de personajes");
		model.addAttribute("personajes", personajeDao.findAll());
		return "characters";
	}

	@GetMapping(value = "/form/{id}")
	public String editarPersonaje(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Personaje personaje = null;
		if (id > 0) {
			personaje = personajeService.findOne(id);
			if (personaje == null) {
				flash.addFlashAttribute("error", "el personaje no existe en la BBD");
				return "redirect:/characters";
			}
		} else {
			flash.addFlashAttribute("error", "el ID del cliente no puede ser cero!");
			return "redirect:/characters";
		}
		model.put("personaje", personaje);
		model.put("titulo", "Editar Personaje");
		return "form";
	}

	@GetMapping(value = "/form")
	public String crearPersonaje(Map<String, Object> model) {
		Personaje personaje = new Personaje();
		model.put("titulo", "Formulario de Personaje");
		model.put("personaje", personaje);
		return "form";
	}

	@GetMapping(value = "/detalles/{id}")
	public String detallesPersonaje(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Personaje personaje = personajeService.findOne(id);
		model.put("personaje", personaje);
		model.put("titulo", "detalle personaje: " + personaje.getNombre());
		return "detalles";
	}

	@PostMapping(value = "/form")
	public String guardarPersonaje(@Valid Personaje personaje, BindingResult result, RedirectAttributes flash,
			@RequestParam("file") MultipartFile imagen, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}

		if (!imagen.isEmpty()) {
			if (personaje.getId() != null && personaje.getId() > 0 && personaje.getImagen() != null
					&& personaje.getImagen().length() > 0) {

				uploadService.delete(personaje.getImagen());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadService.copy(imagen);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido la fotografia correctamente");
			personaje.setImagen(uniqueFilename);

		}
		String mensajeFlash = (personaje.getId() != null) ? "Personaje editado con exito" : "Personaje creado con exito";
		personajeService.save(personaje);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:characters";

	}

	@GetMapping(value = "/delete/{id}")
	public String eliminarPersonaje(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		if (id > 0) {
			Personaje personaje = personajeService.findOne(id);
			personajeService.delete(id);
			flash.addFlashAttribute("success", "personaje eliminado con exito");
			if (uploadService.delete(personaje.getImagen())) {
				flash.addFlashAttribute("info", "Foto " + personaje.getImagen() + " eliminada con exito!");
			}
		}
		return "redirect:/characters";
	}
}
