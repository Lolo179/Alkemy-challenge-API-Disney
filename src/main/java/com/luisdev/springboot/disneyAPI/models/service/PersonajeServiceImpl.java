package com.luisdev.springboot.disneyAPI.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luisdev.springboot.disneyAPI.models.dao.IPersonajeDao;
import com.luisdev.springboot.disneyAPI.models.entity.Personaje;

@Service
public class PersonajeServiceImpl implements IPersonajeService {

	@Autowired
	IPersonajeDao personajeDao;

	@Override
	@Transactional(readOnly = true)
	public List<Personaje> findAll() {
		// TODO Auto-generated method stub
		return personajeDao.findAll();
	}

	@Override
	@Transactional
	public void save(Personaje personaje) {
		personajeDao.save(personaje);
	}

	@Override
	@Transactional(readOnly = true)
	public Personaje findOne(Long id) {
		// TODO Auto-generated method stub
		return personajeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		personajeDao.deleteById(id);
		
	}

}
