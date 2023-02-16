package com.luisdev.springboot.disneyAPI.models.service;

import java.util.List;

import com.luisdev.springboot.disneyAPI.models.entity.Personaje;

public interface IPersonajeService {

	public List<Personaje> findAll();
	
	public void save(Personaje personaje);
	
	public Personaje findOne(Long id);
	
	public void delete(Long id);

}
