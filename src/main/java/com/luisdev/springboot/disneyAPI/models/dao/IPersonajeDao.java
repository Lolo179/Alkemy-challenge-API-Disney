package com.luisdev.springboot.disneyAPI.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisdev.springboot.disneyAPI.models.entity.Personaje;

public interface IPersonajeDao extends JpaRepository<Personaje, Long> {

}
